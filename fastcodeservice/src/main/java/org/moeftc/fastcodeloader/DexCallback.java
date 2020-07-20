package org.moeftc.fastcodeloader;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import org.moeftc.fastcodeservice.Callback;
import org.moeftc.fastcodeservice.CallbackHandlerImpl;


public class DexCallback extends Callback.Stub implements ServiceConnection {

    CallbackHandlerImpl service;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        try {

            this.service = CallbackHandlerImpl.Stub.asInterface(service);
            this.service.register(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    @Override
    public void loadOpModes(String opModes, byte[] dexFile) {
        org.firstinspires.ftc.robotcontroller.moeglobal.opmodeloading.DexOpModeHandler.loadOpModes(opModes, dexFile);
    }

}


//class CallbackHandler extends CallbackHandlerImpl.Stub {
//    ArrayList<Callback> callbacks = new ArrayList<>();
//
//    @Override
//    public void register(Callback callback) {
//        callbacks.add(callback);
//    }
//
//
//}