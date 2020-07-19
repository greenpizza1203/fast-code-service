package com.dex.service;

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
            Log.e(DexServiceKt.TAG, "bound to port: " + serverSocket.getLocalPort());
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    Client.handleClient(socket);
                    socket.close();
                } catch (SocketException e) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() throws IOException {
        Log.e(DexServiceKt.TAG, "Shutting down server");
        serverSocket.close();
    }
}