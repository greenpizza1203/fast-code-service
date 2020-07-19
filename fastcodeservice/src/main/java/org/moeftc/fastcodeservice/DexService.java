package org.moeftc.fastcodeservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import org.moeftc.fastcodeservice.CallbackHandler;
import org.moeftc.fastcodeservice.Server;

import java.io.IOException;


class DexService extends Service {
    public static String TAG = "DexService";

    Server server = new Server();

    public void onCreate() {
        server.start();
    }


    public IBinder onBind(Intent intent) {
        return CallbackHandler.INSTANCE;
    }

    public void onDestroy() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

