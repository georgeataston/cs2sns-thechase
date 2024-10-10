package uk.hotten.thechase.server;

import uk.hotten.thechase.utils.MessageType;
import uk.hotten.thechase.utils.Utils;
import uk.hotten.thechase.utils.QuestionData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket chaser;
    private static DataOutputStream chaserStream;

    private static Socket player;
    private static DataOutputStream playerStream;

    public static GameRound currentRound;

    public static void main(String[] args) throws IOException {
        Utils.print("Starting server...");
        ServerSocket ss = new ServerSocket(17777);

        Utils.print("Awaiting connections.");
        while(chaser == null || player == null) {
            if (chaser == null) {
                chaser = ss.accept();
                chaserStream = new DataOutputStream(chaser.getOutputStream());
                Utils.print("Chaser is connected.");
                sendToChaser(MessageType.ROLE_DESIGNATION, "chaser");
                sendToChaser(MessageType.TEXT, "Welcome. Please wait. You are the chaser.");
                new ServerDataReceiveThread(chaser, true).start();
            } else {
                player = ss.accept();
                playerStream = new DataOutputStream(player.getOutputStream());
                Utils.print("Player is connected.");
                sendToPlayer(MessageType.ROLE_DESIGNATION, "player");
                sendToPlayer(MessageType.TEXT, "Welcome. Please wait. You are the player.");
                new ServerDataReceiveThread(player, false).start();
            }
        }

        start();
    }

    public static void start() {
        currentRound = new GameRound(new QuestionData("Example Question", "Option A", "Option B", "Option C", 'b'));
        currentRound.sendQuestion();
    }

    public static void sendToChaser(MessageType type, String message) {
        try {
            chaserStream.writeInt(type.id);
            chaserStream.writeUTF(message);
            chaserStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToPlayer(MessageType type, String message) {
        try {
            playerStream.writeInt(type.id);
            playerStream.writeUTF(message);
            playerStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendToAll(MessageType type, String message) {
        sendToChaser(type, message);
        sendToPlayer(type, message);
    }

    public static void disconnectChaser() {
        try {
            chaser.close();
            chaser = null;
        } catch (IOException e) {
            Utils.print("Disconnect error: " + e.getMessage());
        }
    }

    public static void disconnectPlayer() {
        try {
            player.close();
            player = null;
        } catch (IOException e) {
            Utils.print("Disconnect error: " + e.getMessage());
        }
    }
}
