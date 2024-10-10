package uk.hotten.thechase.utils;

public class QuestionData {

    private final String question;
    private final String answer_a;
    private final String answer_b;
    private final String answer_c;
    private final char answer_correct;

    public QuestionData(String question, String answer_a, String answer_b, String answer_c, char answer_correct) {
        this.question = question;
        this.answer_a = answer_a;
        this.answer_b = answer_b;
        this.answer_c = answer_c;
        this.answer_correct = answer_correct;
    }

    public String getQuestion() { return question; }
    public String getAnswer_a() { return answer_a; }
    public String getAnswer_b() { return answer_b; }
    public String getAnswer_c() { return answer_c; }
    public char getAnswer_correct() { return answer_correct; }

    public String getAnswerFromChar(char c) {
        if (c == 'a')
            return answer_a;
        else if (c == 'b')
            return answer_b;
        else if (c == 'c')
            return answer_c;

        return null;
    }

}
