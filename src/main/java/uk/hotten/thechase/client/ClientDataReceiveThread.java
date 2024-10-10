package uk.hotten.thechase.client;

import uk.hotten.thechase.sfx.SFXPlayer;
import uk.hotten.thechase.utils.MessageType;
import uk.hotten.thechase.utils.QuestionData;
import uk.hotten.thechase.utils.Utils;

import javax.sound.sampled.Clip;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

import static uk.hotten.thechase.TheChase.gson;

public class ClientDataReceiveThread extends Thread {

    protected Socket socket;
    private Clip timer;

    public ClientDataReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream stream;
        try {
            stream = new DataInputStream(socket.getInputStream());
            while (Client.running) {
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

                    case ROLE_DESIGNATION -> {
                        Client.chaser = data.equalsIgnoreCase("chaser");
                    }

                    case QUESTION_SEND -> {
                        Client.question = gson.fromJson(data, QuestionData.class);
                        Client.sendToServer(MessageType.QUESTION_RECEIVE, "");
                    }

                    case QUESTION_START -> {
                        Utils.print(Client.question.getQuestion());
                        Utils.print("A) " + Client.question.getAnswer_a());
                        Utils.print("B) " + Client.question.getAnswer_b());
                        Utils.print("C) " + Client.question.getAnswer_c());
                        Utils.print("Please type your answer and press ENTER.");
                        Client.allowQuestionInput = true;
                    }

                    case PLAYER_ANSWERED -> {
                        if (data.equalsIgnoreCase("player"))
                            SFXPlayer.playerLockIn();
                        else if (data.equalsIgnoreCase("chaser"))
                            SFXPlayer.chaserLockIn();

                        if (Client.chaser) {
                            if (data.equalsIgnoreCase("player"))
                                Utils.print("The player has answered!");
                            else if (timer == null)
                                Utils.print("The player has 5 seconds to answer.");
                        } else {
                            if (data.equalsIgnoreCase("chaser"))
                                Utils.print("The chaser has answered!");
                            else if (timer == null)
                                Utils.print("The chaser has 5 seconds to answer.");
                        }
                    }

                    case TIMER_START -> {
                        timer = SFXPlayer.gameTimer();
                    }

                    case TIMER_STOP -> {
                        if (timer != null) {
                            timer.stop();
                            timer = null;
                        }
                    }

                    case TIMER_OUTOFTIME -> {
                        SFXPlayer.gameOutOfTime();
                        Utils.print("OUT OF TIME!");
                    }

                    case QUESTION_STOP -> {
                        Client.allowQuestionInput = false;
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
