package uk.hotten.thechase.utils;

import java.util.Arrays;
import java.util.Optional;

public enum MessageType {

    TEXT(0),              // Displays text on the client
    QUESTION_SEND(1),     // Sending the question from the server to the client
    QUESTION_RECEIVE(2),  // Clients confirms receipt of the question
    QUESTION_START(3),    // Sever informs the client to show the question
    PLAYER_ANSWER(4),     // Client sends a player's answer
    PLAYER_ANSWERED(5),   // Server informs client a player has answered
    TIMER_START(6),       // Informs clients to start playing the timer music
    TIMER_STOP(7),        // Informs client to stop playing the timer music
    TIMER_OUTOFTIME(8),   // Tells the client the timer has timed out
    QUESTION_STOP(9),     // Server sends to prevent question answering
    RESULTS_PLAYER(10),   // Plays the player answer sfx
    RESULTS_CORRECT(11),  // Plays the correct answer sfx
    RESULTS_CHASER(12),   // Plays the chaser answer sfx
    DISCONNECT(13),       // Disconnects the client from the server
    ROLE_DESIGNATION(14); // Lets the client know what they are

    public int id;

    private MessageType(int id) {
        this.id = id;
    }

    public static Optional<MessageType> valueOf(int value) {
        return Arrays.stream(values())
                .filter(msgId -> msgId.id == value)
                .findFirst();
    }

}
