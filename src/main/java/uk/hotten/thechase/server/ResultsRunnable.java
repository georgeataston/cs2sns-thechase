package uk.hotten.thechase.server;

import uk.hotten.thechase.utils.MessageType;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class ResultsRunnable implements Runnable {

    @Override
    public void run() {
        char playerAnswer = Server.currentRound.playerAnswer;
        char chaserAnswer = Server.currentRound.chaserAnswer;
        char correctAnswer = Server.currentRound.question.getAnswer_correct();
        try {
            TimeUnit.SECONDS.sleep(2);
            if (Server.currentRound.playerAnswer == 'Z') {
                Server.sendToPlayer(MessageType.TEXT, "You did not answer.");
                Server.sendToChaser(MessageType.TEXT, "The player did not answer.");
            } else {
                Server.sendToPlayer(MessageType.TEXT, "You put...");
                Server.sendToChaser(MessageType.TEXT, "The player put...");
                TimeUnit.SECONDS.sleep(2);
                Server.sendToAll(MessageType.RESULTS_PLAYER, "");
                Server.sendToAll(MessageType.TEXT, String.valueOf(playerAnswer).toUpperCase() + ") " + Server.currentRound.question.getAnswerFromChar(playerAnswer));
            }
            TimeUnit.SECONDS.sleep(3);
            Server.sendToAll(MessageType.TEXT, "The correct answer is...");
            TimeUnit.SECONDS.sleep(2);
            Server.sendToAll(MessageType.RESULTS_CORRECT, "");
            Server.sendToAll(MessageType.TEXT, String.valueOf(correctAnswer).toUpperCase() + ") " + Server.currentRound.question.getAnswerFromChar(correctAnswer));

            TimeUnit.SECONDS.sleep(3);
            if (chaserAnswer == 'Z') {
                Server.sendToChaser(MessageType.TEXT, "You did not answer.");
                Server.sendToPlayer(MessageType.TEXT, "The chaser did not answer.");
            } else {
                Server.sendToChaser(MessageType.TEXT, "You put...");
                Server.sendToPlayer(MessageType.TEXT, "The chaser put...");
                TimeUnit.SECONDS.sleep(2);
                Server.sendToAll(MessageType.RESULTS_CHASER, "");
                Server.sendToAll(MessageType.TEXT, String.valueOf(chaserAnswer).toUpperCase() + ") " + Server.currentRound.question.getAnswerFromChar(chaserAnswer));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
