package com.werewolves.quizsection.entities;

import java.util.ArrayList;
import java.util.Collection;

public class Question {
    private int id;
    private String title;
    private Collection<Choice> choices = null;
    private Choice correctChoice;
    private Quiz quiz;

    public Question(){}
    public Question(int id) {
        this.id = id;
    }

    public Question(String title, Choice correctChoice, Quiz quiz) {
        this.title = title;
        this.correctChoice = correctChoice;
        this.quiz = quiz;
    }

    public Question(int id, String title, Choice correctChoice, Quiz quiz) {
        this.id = id;
        this.title = title;
        this.correctChoice = correctChoice;
        this.quiz = quiz;
    }

    public Question(int id, String title, Collection<Choice> choices, Choice correctChoice, Quiz quiz) {
        this.id = id;
        this.title = title;
        this.choices = choices;
        this.correctChoice = correctChoice;
        this.quiz = quiz;
    }

    public Question(String title, Collection<Choice> choices, Choice correctChoice, Quiz quiz) {
        this.title = title;
        this.choices = choices;
        this.correctChoice = correctChoice;
        this.quiz = quiz;
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

    public Collection<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Collection<Choice> choices) {
        this.choices = choices;
    }

    public void addChoice(Choice choice)
    {
        if(this.choices == null)
            this.choices = new ArrayList<>();

        this.choices.add(choice);
    }
    public Choice getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(Choice correctChoice) {
        this.correctChoice = correctChoice;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

}
