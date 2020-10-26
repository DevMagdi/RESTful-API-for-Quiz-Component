package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.QuestionAnswerPair;
import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.entities.Submission;
import com.werewolves.quizsection.models.SubmissionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Service
public class SubmissionService {
    @Autowired
    @Qualifier("MySQLSubmissionModel")
    SubmissionModel submissionModel;

    public Collection<Submission> getAllSubmissions()
    {

        return this.submissionModel.getAllSubmissions();
    }

    public Submission getSubmissionByID(int id )
    {

        return this.submissionModel.getSubmissionByID(id);
    }


    public Collection<Submission> getSubmissionsForQuiz(int quizId )
    {
        return this.submissionModel.getSubmissionForQuiz(quizId);
    }

    public Collection<Submission> getSubmissionsForUser(int userId )
    {

        return this.submissionModel.getSubmissionForUser(userId);
    }

    public int addSubmission(int userId ,int quizId ,Collection<QuestionAnswerPair> answersIds )
    {
        int score = evaluateSubmission(quizId, answersIds);

        // datetime calculate
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = formatter.format(date);


        return this.submissionModel.addSubmission(new Submission(score,userId, new Quiz(quizId), strDate));
    }

    private int evaluateSubmission(int quizId, Collection<QuestionAnswerPair> answersIds)
    {
        if(answersIds.size()<1)
            return -1;

        ArrayList<Integer> questionsIds = new ArrayList<>();
        for(QuestionAnswerPair qAPair : answersIds)
            questionsIds.add(qAPair.getQuestionId());

        QuestionService questionService = new QuestionService();
        Map<Integer, Integer> questionCorrectAnswerMap =  questionService.getCorrectAnswerFor(questionsIds);


        int score = 0, correctAnswerId;
        for(QuestionAnswerPair qAPair : answersIds)
        {
            correctAnswerId = questionCorrectAnswerMap.get(qAPair.getQuestionId());
            if(correctAnswerId != 0 && correctAnswerId == qAPair.getAnswerId())
                score++;
        }
        return score;
    }
}
