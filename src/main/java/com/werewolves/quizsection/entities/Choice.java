package com.werewolves.quizsection.entities;

public class Choice
{
    private int id;
    private String title;
    private Question question;

    public Choice(){}

    public Choice(int id) {
        this.id = id;
    }

    public Choice(String title) {
        this.title = title;
    }

    public Choice(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Choice(String title, Question question) {
        this.title = title;
        this.question = question;
    }

    public Choice(int id, String title, Question question) {
        this.id = id;
        this.title = title;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
