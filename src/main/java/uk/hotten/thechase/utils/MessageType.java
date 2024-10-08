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
    TIMER(6),             // Server sending timer countdown
    QUESTION_STOP(7),     // Server sends to prevent question answering
    RESULTS_SEND(8),      // Server sends the results for the clients to show
    DISCONNECT(9);         // Disconnects the client from the server


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
