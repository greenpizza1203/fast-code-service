package org.moeftc.fastcodeloader;

import android.util.ArrayMap;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMetaAndClass;

import java.util.Map;

import static org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta.Flavor.AUTONOMOUS;
import static org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta.Flavor.TELEOP;

public class DexOpModeHandler {
    public static Map<String, OpModeMetaAndClass> getMetaMap(String opmodeString, ClassLoader classLoader) {
        String[] opmodes = opmodeString.split("\n");
        Map<String, OpModeMetaAndClass> metaMap = new ArrayMap<>();

        addFlavor(AUTONOMOUS, opmodes[0], metaMap, classLoader);
        if (opmodes.length > 1) addFlavor(TELEOP, opmodes[1], metaMap, classLoader);


        return metaMap;
    }


    private static void addFlavor(OpModeMeta.Flavor flavor, String opModes, Map<String, OpModeMetaAndClass> map, ClassLoader classLoader) {
        if (opModes.isEmpty()) return;

        String[] autoDetails = opModes.split("/");

        for (int i = 0; i < autoDetails.length; i += 2) {
//            boolean isMagic = autoDetails[i].endsWith("*");
//            if (isMagic) autoDetails[i] = autoDetails[i].substring(0, autoDetails[i].length() - 1);
            OpModeMetaAndClass meta = new OpModeMetaAndClass(new OpModeMeta(autoDetails[i], flavor), getClass(classLoader, autoDetails[i + 1]));
//            if (isMagic) map.put("magic", meta);
            map.put(autoDetails[i], meta);
        }
    }


    @SuppressWarnings("unchecked")
    private static Class<OpMode> getClass(ClassLoader classLoader, String path) {
        try {
            return (Class<OpMode>) classLoader.loadClass(path);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadOpModes(String opModes, byte[] dexFile) {
        ClassLoader classLoader = DexHandler.getClassLoader(dexFile);
        Map<String, OpModeMetaAndClass> metaMap = getMetaMap(opModes, classLoader);
        ReflectionHolder.replaceOpModes(metaMap);

    }
}
