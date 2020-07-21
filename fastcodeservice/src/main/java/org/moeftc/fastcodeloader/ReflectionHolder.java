package org.moeftc.fastcodeloader;

import com.qualcomm.ftccommon.FtcEventLoop;
import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMetaAndClass;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMetaAndInstance;
import org.firstinspires.ftc.robotcore.internal.opmode.RegisteredOpModes;
import org.moeftc.FastCode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.moeftc.fastcodeservice.R;

public class ReflectionHolder {

    private static final List<String> justDefault = Collections.singletonList(OpModeManager.DEFAULT_OP_MODE_NAME);

    public static LinkedHashMap<String, OpModeMetaAndClass> opModeClasses;
    public static LinkedHashMap<String, OpModeMetaAndInstance> opModesInstances;
    public static Object opModesLock;

    public static Method sendUIStateMethod;

    @SuppressWarnings("unchecked")
    public static void init() {
        try {

            RegisteredOpModes instance = RegisteredOpModes.getInstance();
            Field opModeClasses1 = instance.getClass().getDeclaredField("opModeClasses");
            Field opModeInstances1 = instance.getClass().getDeclaredField("opModeInstances");
            Field opModesLock1 = instance.getClass().getDeclaredField("opModesLock");
            opModesLock1.setAccessible(true);
            opModeClasses1.setAccessible(true);
            opModeInstances1.setAccessible(true);
            opModeClasses = (LinkedHashMap<String, OpModeMetaAndClass>) opModeClasses1.get(instance);
            opModesInstances = (LinkedHashMap<String, OpModeMetaAndInstance>) opModeInstances1.get(instance);
            opModesLock = opModesLock1.get(instance);
            sendUIStateMethod = FtcEventLoop.class.getDeclaredMethod("sendUIState");

        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        }


    }


    public static void replaceOpModes(Map<String, OpModeMetaAndClass> opModes) {
        synchronized (opModesLock) {
            clearOpModes();
            loadOpModes(opModes);
        }
        ReflectionHolder.finishUpdate();

    }

    /*
     * synchronize on opModesLock
     */
    private static void loadOpModes(Map<String, OpModeMetaAndClass> opModes) {


        opModeClasses.putAll(opModes);

    }

    /*
     * synchronize on opModesLock
     */
    private static void clearOpModes() {
        opModeClasses.keySet().retainAll(justDefault);
        opModesInstances.keySet().retainAll(justDefault);
    }

    public static void finishUpdate() {
        updateUI();
        playInstalledSound();
    }

    public static void updateUI() {

        FtcRobotControllerActivity activity = FastCode.getActivity();
        if (activity == null) return;
        try {
            sendUIStateMethod.invoke(activity.eventLoop);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

//        Method sendUIStateMethod = FtcEventLoop.class.getDeclaredMethod("sendUIState");
    }

    public static void playInstalledSound() {
        SoundPlayer.getInstance().stopPlayingAll();
        SoundPlayer.getInstance().startPlaying(FastCode.getActivity(), R.raw.firecode, new SoundPlayer.PlaySoundParams(false), null, null);
    }
//    public static void initOpMode(OpModeMetaAndClass opMode) {
//        Log.e("happening", opMode.meta.name);
//        ActivityReferenceHolder.activityRefHolder.get().eventLoop.getOpModeManager().initActiveOpMode(opMode.meta.name);
//
//    }
}

