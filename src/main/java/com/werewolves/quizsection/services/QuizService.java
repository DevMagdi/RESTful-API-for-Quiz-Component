package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.models.QuizModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class QuizService {

    @Autowired
    @Qualifier ("MySQLQuizModel")
    QuizModel quizModel ;


    public Collection<Quiz> getAllQuizzes()
    {
        return this.quizModel.getAllQuizzes()  ;
    }
    public Quiz getQuizByID(int id )
    {
        return this.quizModel.getQuizByID(id) ;
    }

    public Collection<Quiz> getQuizBySkill(int skillId)
    {
        return this.quizModel.getQuizBySkill(skillId) ;
    }

    public int addQuiz(Quiz quiz)
    {
        int q = this.quizModel.addQuiz(quiz) ;
        return q;
    }
    public Boolean updateQuiz(Quiz quiz)
    {
        return this.quizModel.updateQuiz(quiz) ;
    }

    public Boolean deleteQuiz(int quizId )
    {

        return this.quizModel.deleteQuiz(quizId) ;
    }
}
