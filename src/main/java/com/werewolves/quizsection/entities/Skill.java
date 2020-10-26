package com.werewolves.quizsection.entities;

import java.util.Collection;

public class Skill {
    private int id;
    private String name;
    private Collection<Quiz> quizzes;

    public Skill(){}
    public Skill(int id) {
        this.id = id;
    }

    public Skill(String name) {
        this.name = name;
    }

    public Skill(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Collection<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
