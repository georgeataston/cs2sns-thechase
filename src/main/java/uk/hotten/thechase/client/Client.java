package uk.hotten.thechase.client;

import uk.hotten.thechase.utils.MessageType;
import uk.hotten.thechase.utils.Utils;
import uk.hotten.thechase.utils.QuestionData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

import static uk.hotten.thechase.TheChase.gson;

public class Client {

    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    private static QuestionData question;

    public static void main(String[] args) {
        try {
            Utils.print("Loading client and connecting to server...");
            socket = new Socket("127.0.0.1", 17777);
            Utils.print("Connected.");
            Utils.print("Additional Commands:\n Leave: Disconnects You From The Server\n Rules: Shows you the ruleset for the game.\n Help: Guidance on how to play."); //\n makes spaces, just the commands for helping the player out

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream stream = new DataInputStream(socket.getInputStream());
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
                        question = gson.fromJson(data, QuestionData.class);
                        sendToServer(MessageType.QUESTION_RECEIVE, "");
                    }

                    case QUESTION_START -> {
                        Utils.print(question.getQuestion());
                    }

                    default -> {
                        Utils.print("NYIPML: " + data);
                    }
                }
            }
        } catch (Exception e) {
            Utils.print("Connection failure: " + e.getMessage());
        }
    }

    private static void sendToServer(MessageType type, String message) {
        try {
            dataOutputStream.writeInt(type.id);
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
