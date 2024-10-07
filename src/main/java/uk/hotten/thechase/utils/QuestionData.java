package uk.hotten.thechase.utils;

public class QuestionData {

    private final String question;
    private final String answer_a;
    private final String answer_b;
    private final String answer_c;

    public QuestionData(String question, String answer_a, String answer_b, String answer_c) {
        this.question = question;
        this.answer_a = answer_a;
        this.answer_b = answer_b;
        this.answer_c = answer_c;
    }

    public String getQuestion() { return question; }
    public String getAnswer_a() { return answer_a; }
    public String getAnswer_b() { return answer_b; }
    public String getAnswer_c() { return answer_c; }

}
