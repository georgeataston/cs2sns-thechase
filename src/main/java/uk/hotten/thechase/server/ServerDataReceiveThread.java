package uk.hotten.thechase.server;

import uk.hotten.thechase.utils.Utils;
import uk.hotten.thechase.utils.MessageType;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class ServerDataReceiveThread extends Thread {

    protected Socket socket;
    protected boolean chaser;

    public ServerDataReceiveThread(Socket socket, boolean chaser) {
        this.socket = socket;
        this.chaser = chaser;
    }

    @Override
    public void run() {
        DataInputStream stream;
        try {
            stream = new DataInputStream(socket.getInputStream());
            boolean run = true;
            while (run) {
                int typeId = stream.readInt();
                String data = stream.readUTF();
                Optional<MessageType> type = MessageType.valueOf(typeId);
                if (type.isEmpty()) {
                    Utils.print("Received message without a valid type: " + data);
                    return;
                }

                switch (type.get()) {
                    case QUESTION_RECEIVE -> {
                        if (chaser)
                            Server.currentRound.chaserIsReady();
                        else
                            Server.currentRound.playerIsReady();
                    }

                    case PLAYER_ANSWER -> {
                        if (chaser) {
                            Server.sendToAll(MessageType.PLAYER_ANSWERED, "chaser");
                            Server.currentRound.chaserAnswer = data.charAt(0);
                            Utils.print("Chaser has answered " + data);
                        } else {
                            Server.sendToAll(MessageType.PLAYER_ANSWERED, "player");
                            Server.currentRound.playerAnswer = data.charAt(0);
                            Utils.print("Player has answered " + data);
                        }

                        Server.currentRound.checkAndRunTimer();
                    }

                    case DISCONNECT -> {
                        if (chaser) {
                            Server.disconnectChaser();
                            Utils.print("Chaser has quit.");
                        } else {
                            Server.disconnectPlayer();
                            Utils.print("Player has quit.");
                        }
                        run = false;
                    }

                    default -> {
                        Utils.print("NYIMPL: " + data);
                    }
                }
            }
        } catch (IOException e) {
            Utils.error("Thread connection error: " + e.getMessage());
        }
    }
}
