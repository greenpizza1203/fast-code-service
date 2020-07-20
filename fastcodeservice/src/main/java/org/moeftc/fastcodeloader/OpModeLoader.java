package org.moeftc.fastcodeloader;

import com.qualcomm.ftccommon.SoundPlayer;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;
import org.moeftc.FastCode;
import org.moeftc.fastcodeservice.R;


public class OpModeLoader {


    public static void init(FtcRobotControllerActivity context) {
        ReflectionHolder.init();
        DexHandler.init(context);
//     SharedPrefHandler.init(context);
    }

    public static void finishUpdate() {
        ReflectionHolder.updateUI();
        playInstalledSound();
    }

    public static void playInstalledSound() {
        SoundPlayer.getInstance().stopPlayingAll();
        SoundPlayer.getInstance().startPlaying(FastCode.getActivity(), R.raw.firecode, new SoundPlayer.PlaySoundParams(false), null, null);

    }


}
