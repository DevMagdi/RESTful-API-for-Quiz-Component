package com.werewolves.quizsection.models;
import com.werewolves.quizsection.entities.Quiz;

import java.util.Collection;

public abstract class QuizModel extends Model {
    public abstract Collection<Quiz> getAllQuizzes();
    public abstract Quiz getQuizByID(int id);
    public abstract int addQuiz(Quiz quiz);
    public abstract Boolean updateQuiz(Quiz quiz);
    public abstract Boolean deleteQuiz(int quizId );
    public abstract Collection<Quiz> getQuizBySkill(int skillId) ;
}