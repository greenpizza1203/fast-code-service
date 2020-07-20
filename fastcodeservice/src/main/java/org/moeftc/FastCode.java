package org.moeftc;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.moeftc.fastcodeloader.DexHandler;
import org.moeftc.fastcodeloader.OpModeLoader;

import java.lang.ref.WeakReference;

public class FastCode {
    private static boolean inited = false;
    public static WeakReference<FtcRobotControllerActivity> activityRef;

    public static void init(FtcRobotControllerActivity context) {
        if (inited) return;
        inited = true;
        activityRef = new WeakReference<>(context);
        OpModeLoader.init(context);
    }

    public static FtcRobotControllerActivity getActivity() {
        return activityRef.get();
    }
}
