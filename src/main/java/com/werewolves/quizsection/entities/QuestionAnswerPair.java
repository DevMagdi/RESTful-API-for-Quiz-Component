package com.werewolves.quizsection.entities;

public class QuestionAnswerPair {
    private int questionId;
    private int answerId;

    public QuestionAnswerPair() {}

    public QuestionAnswerPair(int questionId, int answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
