package uk.hotten.thechase;

public enum MessageType {

    TEXT(0),
    QUESTION_SEND(1),
    QUESTION_RECEIVE(2),
    QUESTION_START(3),
    PLAYER_ANSWER(4),
    TIMER(5),
    QUESTION_STOP(6);


    public int id;

    private MessageType(int id) {
        this.id = id;
    }

}
