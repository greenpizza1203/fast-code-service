package org.moeftc;

import android.app.Activity;
import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.AnnotatedOpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.moeftc.fastcodeloader.DexHandler;
import org.moeftc.fastcodeloader.ReflectionHolder;

import java.lang.ref.WeakReference;

public class FastCode {
    private static boolean inited = false;
    public static WeakReference<FtcRobotControllerActivity> activityRef;

    @OpModeRegistrar
    public static void init(Context context, AnnotatedOpModeManager manager) {
        if (inited) return;
        Activity root = AppUtil.getInstance().getRootActivity();
//        String name = root.getClass().getName();
//        Log.e("Found it", name);
        inited = true;
        activityRef = new WeakReference<>((FtcRobotControllerActivity) root);
        ReflectionHolder.init();
        DexHandler.init();
//     SharedPrefHandler.init(context);
    }

    public static FtcRobotControllerActivity getActivity() {
        return activityRef.get();
    }
}
