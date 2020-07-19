package org.moeftc.fastcodeservice;

import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

class CallbackHandler extends CallbackHandlerImpl.Stub {
    static List<Callback> callbacks = new ArrayList<>();
    public static CallbackHandler INSTANCE;

    static {
        INSTANCE = new CallbackHandler();
    }

    public void register(Callback callback) {
        callbacks.add(callback);
    }

//    override fun respond(code: Int) {
////        Log.e("writing", "data$code")
//        lastSocket.getOutputStream().write(code)
//        lastSocket.getOutputStream().close()
//    }

    public static void broadcast(String opModes, byte[] dexFile) throws RemoteException {
        for (Callback callback : callbacks) {
            callback.loadOpModes(opModes, dexFile);
        }
    }

}

