package uk.hotten.thechase.server;

import uk.hotten.thechase.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnectionThread extends Thread {

    protected Socket socket;

    public ServerConnectionThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream stream;
        try {
            stream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            Utils.error("Thread connection error: " + e.getMessage());
        }
    }
}
