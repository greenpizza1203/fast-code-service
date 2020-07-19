package com.dex.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.dex.service.CallbackHandler

const val TAG = "DexService"

class DexService : Service() {
    var server = Server()
    override fun onCreate() {
        server.start()
    }


    override fun onBind(intent: Intent): IBinder {
        return CallbackHandler
    }

    override fun onDestroy() {
        server.close()
    }


}

