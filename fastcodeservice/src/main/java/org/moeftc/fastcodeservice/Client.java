package com.dex.service;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import static com.dex.service.Utilities.readInt;
import static com.dex.service.Utilities.readNBytes;

class Client {
    public static Socket lastSocket;

    public static void handleClient(Socket socket) throws IOException {
        lastSocket = socket;
        InputStream inputStream = socket.getInputStream();
        boolean verify = Client.verifySignature(inputStream);
        if (!verify) {
            Log.e(DexServiceKt.TAG, "signature mismatch");
            return;
        }
        String opModes = getOpModes(inputStream);
        byte[] dexFile = Utilities.readAllBytes(inputStream);

        CallbackHandler.broadcast(opModes, dexFile);
    }

    private static String getOpModes(InputStream inputStream) throws IOException {
        int stringLength = readInt(inputStream);
        return new String(readNBytes(inputStream, stringLength));
    }

    public static int[] signature = new int[]{3, 6, 5};

    private static boolean verifySignature(InputStream inputStream) throws IOException {
        for (int i : Client.signature) {

            if (i != inputStream.read()) return false;
        }
        return true;
    }


}



