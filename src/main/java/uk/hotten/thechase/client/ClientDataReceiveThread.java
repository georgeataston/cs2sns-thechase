package uk.hotten.thechase.client;

import uk.hotten.thechase.utils.MessageType;
import uk.hotten.thechase.utils.QuestionData;
import uk.hotten.thechase.utils.Utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

import static uk.hotten.thechase.TheChase.gson;

public class ClientDataReceiveThread extends Thread {

    protected Socket socket;

    public ClientDataReceiveThread(Socket socket) {
        this.socket = socket;
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
                    case TEXT -> {
                        Utils.print(data);
                    }

                    case QUESTION_SEND -> {
                        Client.question = gson.fromJson(data, QuestionData.class);
                        Client.sendToServer(MessageType.QUESTION_RECEIVE, "");
                    }

                    case QUESTION_START -> {
                        Utils.print(Client.question.getQuestion());
                    }

                    default -> {
                        Utils.print("NYIPML: " + data);
                    }
                }
            }
        } catch (IOException e) {
            Utils.error("Thread connection error: " + e.getMessage());
        }
    }
}
