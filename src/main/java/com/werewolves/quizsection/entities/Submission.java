package com.werewolves.quizsection.entities;

import java.util.Collection;

public class Submission {
    private int id;
    private int score;
    private int userId;
    private String submitTime;
    private Quiz quiz;

    public Submission(int id) {
        this.id = id;
    }

    public Submission(int score, int userId, Quiz quiz) {
        this.score = score;
        this.userId = userId;
        this.quiz = quiz;
    }

    public Submission(int id, int score, int userId, Quiz quiz) {
        this.id = id;
        this.score = score;
        this.userId = userId;
        this.quiz = quiz;

    }

    public Submission(int score, int userId, Quiz quiz, String submitTime) {
        this.score = score;
        this.userId = userId;
        this.quiz = quiz;
        this.submitTime = submitTime;
    }

    public Submission(int id, int score, int userId, Quiz quiz, String submitTime) {
        this.id = id;
        this.score = score;
        this.userId = userId;
        this.submitTime = submitTime;
        this.quiz = quiz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }


    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
