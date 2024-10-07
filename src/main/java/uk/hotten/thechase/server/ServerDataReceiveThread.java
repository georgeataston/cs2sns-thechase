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
            while (true) {
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
