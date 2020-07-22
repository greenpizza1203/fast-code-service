package org.moeftc.fastcodeservice;

import android.os.RemoteException;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server extends Thread {
    private ServerSocket serverSocket;

    public void run() {
        try {
            serverSocket = new ServerSocket(42069);
            Log.e(DexService.TAG, "bound to port: " + serverSocket.getLocalPort());
            //noinspection InfiniteLoopStatement
            while (true) {
                new Client(serverSocket.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() throws IOException {
        Log.e(DexService.TAG, "Shutting down server");
        serverSocket.close();
    }
}