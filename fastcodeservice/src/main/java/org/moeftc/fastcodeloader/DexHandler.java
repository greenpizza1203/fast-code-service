package org.moeftc.fastcodeloader;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.InMemoryDexClassLoader;
import dalvik.system.PathClassLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static android.content.Context.BIND_AUTO_CREATE;

public class DexHandler {
    private static final String tempFile = Environment.getDataDirectory() + "/temp.dex";
    private static TeamLessClassLoader parent;

    public static void init(Activity context) {
        parent = new TeamLessClassLoader(context.getClassLoader());
        bindToService(context);
    }

    private static void bindToService(Activity context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("org.moeftc.fastcodeservice", "com.dex.service.DexService"));
        DexCallback conn = new DexCallback();
        context.bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public static BaseDexClassLoader getClassLoader(byte[] dexFile) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new InMemoryDexClassLoader(ByteBuffer.wrap(dexFile), parent);
        } else {
            saveToTempFile(dexFile);
            return new PathClassLoader(
                    tempFile,
                    parent
            );
        }
    }

    private static void saveToTempFile(byte[] dexFile) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            fileOutputStream.write(dexFile);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
