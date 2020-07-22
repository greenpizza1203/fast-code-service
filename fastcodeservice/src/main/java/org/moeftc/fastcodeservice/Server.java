package org.moeftc.fastcodeservice;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;

    public void run() {
        try {
            serverSocket = new ServerSocket(42069);
            Log.e(DexService.TAG, "bound to port: " + serverSocket.getLocalPort());
            //noinspection InfiniteLoopStatement
            while (true) {
                Log.e(DexService.TAG, "waiting for connection");

                Socket accept = serverSocket.accept();
                Log.e(DexService.TAG, "finally");

                new Client(accept);
                Log.e(DexService.TAG, "here we go again");


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