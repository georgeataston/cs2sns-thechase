package uk.hotten.thechase.server;

import uk.hotten.thechase.utils.MessageType;

import java.util.concurrent.TimeUnit;

public class TimerRunnable implements Runnable {

    private int timeRemaining = 5;

    @Override
    public void run() {
        try {
            while (timeRemaining > 0) {
                if (Server.currentRound.playerAnswer != 'Z' && Server.currentRound.chaserAnswer != 'Z')
                    return;

                if (Server.currentRound.playerAnswer == 'Z')
                    Server.sendToPlayer(MessageType.TEXT, "TIME LEFT TO ANSWER: " + timeRemaining + "s");

                if (Server.currentRound.chaserAnswer == 'Z')
                    Server.sendToChaser(MessageType.TEXT, "TIME LEFT TO ANSWER: " + timeRemaining + "s");

                TimeUnit.SECONDS.sleep(1);
                timeRemaining--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Server.currentRound.playerAnswer != 'Z' && Server.currentRound.chaserAnswer != 'Z')
            return;

        Server.sendToAll(MessageType.QUESTION_STOP, "");
        Server.sendToAll(MessageType.TIMER_OUTOFTIME, "");

    }
}
