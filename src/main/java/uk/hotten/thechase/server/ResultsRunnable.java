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

            if (playerAnswer == correctAnswer)
                Server.playerScore++;
            if (chaserAnswer == correctAnswer)
                Server.chaserScore++;

            if (Server.chaserScore >= 3 && Server.playerScore >= 3) {
                if (Server.playerScore > Server.chaserScore) {
                    Server.sendToAll(MessageType.PLAYER_WIN, "player");
                    Server.sendToAll(MessageType.TEXT, "GAME OVER! The player wins " + Server.playerScore + "-" + Server.chaserScore);
                    return;
                } else if (Server.chaserScore > Server.playerScore) {
                    Server.sendToAll(MessageType.PLAYER_WIN, "chaser");
                    Server.sendToAll(MessageType.TEXT, "GAME OVER! The chaser wins " + Server.chaserScore + "-" + Server.playerScore);
                    return;
                }
            } else {
                if (Server.playerScore == 3) {
                    Server.sendToAll(MessageType.PLAYER_WIN, "player");
                    Server.sendToAll(MessageType.TEXT, "GAME OVER! The player wins " + Server.playerScore + "-" + Server.chaserScore);
                    return;
                } else if (Server.chaserScore == 3) {
                    Server.sendToAll(MessageType.PLAYER_WIN, "chaser");
                    Server.sendToAll(MessageType.TEXT, "GAME OVER! The chaser wins " + Server.chaserScore + "-" + Server.playerScore);
                    return;
                }
            }

            TimeUnit.SECONDS.sleep(3);
            Server.sendToAll(MessageType.TEXT, "The scores are now: ");
            Server.sendToAll(MessageType.TEXT, "Player: " + Server.playerScore);
            Server.sendToAll(MessageType.TEXT, "Chaser: " + Server.chaserScore);
            Server.sendToAll(MessageType.TEXT, "Your next question will come in 5 seconds...");
            TimeUnit.SECONDS.sleep(5);
            Server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
