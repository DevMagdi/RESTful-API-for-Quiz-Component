package com.werewolves.quizsection.services;


import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.models.QuestionModel;
import com.werewolves.quizsection.models.mySQLModels.MySQLQuestionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
public class QuestionService {
    @Autowired
    @Qualifier("MySQLQuestionModel")
    QuestionModel questionModel;

    public Collection<Question> getAllQuestions(int quizId) {
        Collection<Question> questions = this.questionModel.getAllQuestions(quizId);
        return questions;
    }

    public Question getQuestionByID(int id) {
        Question question = this.questionModel.getQuestionByID(id);
        if(question == null){
            /** will be modified **/
            return null;
        }else{
            return question;
        }
    }

    public int addQuestion(Question question) {
        return  this.questionModel.addQuestion(question);
    }

    public boolean updateQuestionByID(Question question) {
        if(this.questionModel.updateQuestionByID(question)) {
            return true;
        }
        return false;
    }

    public boolean deleteQuestionByID(int id) {
        if(this.questionModel.deleteQuestionByID(id)) {
            return true;
        }
        return false;
    }

    public Map<Integer, Integer> getCorrectAnswerFor(ArrayList<Integer> questionsIds)
    {
        if(this.questionModel == null)
            this.questionModel = new MySQLQuestionModel();
        Map<Integer, Integer> map = this.questionModel.getCorrectAnswerFor(questionsIds);
        System.out.println(map);
        return map;
    }
}
