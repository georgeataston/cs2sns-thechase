package uk.hotten.thechase.server;

import uk.hotten.thechase.TheChase;
import uk.hotten.thechase.utils.MessageType;
import uk.hotten.thechase.utils.QuestionData;
import uk.hotten.thechase.utils.Utils;

public class GameRound {

    public QuestionData question;

    private boolean chaserReady = false;
    private boolean playerReady = false;
    private volatile boolean questionSent = false;

    private char chaserAnswer = 'Z';
    private char playerAnswer = 'Z';

    public GameRound(QuestionData question) {
        this.question = question;
    }

    public void sendQuestion() {
        Server.sendToAll(MessageType.QUESTION_SEND, TheChase.gson.toJson(question));
    }

    public synchronized void chaserIsReady() {
        chaserReady = true;
        Utils.print("Chaser has confirmed receipt of question.");
        if (playerReady)
            progressToQuestion();
    }

    public synchronized void playerIsReady() {
        playerReady = true;
        Utils.print("Player has confirmed receipt of question.");
        if (chaserReady)
            progressToQuestion();
    }

    public synchronized void progressToQuestion() {
        Utils.print("Clients are ready, showing question.");
        Server.sendToAll(MessageType.QUESTION_START, "");
        questionSent = true;
    }

}
