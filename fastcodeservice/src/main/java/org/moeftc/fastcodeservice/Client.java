package org.moeftc.fastcodeservice;

import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static org.moeftc.fastcodeservice.Utilities.readInt;
import static org.moeftc.fastcodeservice.Utilities.readNBytes;

class Client extends Thread {
    public Socket client;

    public Client(Socket client) {
        Log.e("client connection", "opened");
        this.client = client;
        this.start();

    }

    private static String getOpModes(InputStream inputStream) throws IOException {
        int stringLength = readInt(inputStream);
        return new String(readNBytes(inputStream, stringLength));
    }

    public static int[] signature = new int[]{3, 6, 5};

    private static boolean verifySignature(InputStream inputStream) throws IOException {
        for (int i : Client.signature) if (i != inputStream.read()) return false;
        return true;
    }


    @Override
    public void run() {
        try {
            InputStream inputStream = client.getInputStream();
            boolean verify = Client.verifySignature(inputStream);
            if (!verify) {
                Log.e(DexService.TAG, "signature mismatch");
                client.close();
                return;
            }
            String opModes = getOpModes(inputStream);
            byte[] dexFile = Utilities.readAllBytes(inputStream);
            client.close();

            CallbackHandler.broadcast(opModes, dexFile);
        } catch (IOException | RemoteException e) {
            e.printStackTrace();
        }
    }
}



