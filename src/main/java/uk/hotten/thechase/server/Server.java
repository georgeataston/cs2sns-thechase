package uk.hotten.thechase.server;

import uk.hotten.thechase.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket chaser;
    private static DataOutputStream chaserStream;

    private static Socket player;
    private static DataOutputStream playerStream;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(17777);

        Utils.print("Awaiting connections.");
        while(chaser == null || player == null) {
            if (chaser == null) {
                chaser = ss.accept();
                chaserStream = new DataOutputStream(chaser.getOutputStream());
                Utils.print("Chaser is connected.");
                chaserStream.writeUTF("Welcome. Please wait.");
                chaserStream.flush();
                new ServerConnectionThread(chaser).start();
            } else {
                player = ss.accept();
                playerStream = new DataOutputStream(player.getOutputStream());
                Utils.print("Player is connected.");
                new ServerConnectionThread(player).start();
            }
        }

        start();
    }

    public static void start() {

    }

    private static void sendToAll(String message) {
        try {
            chaserStream.writeUTF(message);
            playerStream.writeUTF(message);
            chaserStream.flush();
            playerStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
