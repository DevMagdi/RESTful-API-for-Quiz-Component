package com.werewolves.quizsection.models.mySQLModels;


import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.models.QuestionModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@Qualifier("MySQLQuestionModel")
public class MySQLQuestionModel extends QuestionModel {
    private String tableName = "questions";


    @Override
    public Collection<Question> getAllQuestions(int quizId) {
        MySQLConnector.openConnection();
        String q = "SELECT " +
                "q.id question_id, " +
                "q.question_title, " +
                "q.quiz_id, " +
                "q.correct_answer_id, " +
                "c.id choice_id, " +
                "c.answer " +
                "FROM " +
                this.tableName+" q LEFT JOIN choices c " +
                "ON q.id = c.question_id " +
                "WHERE q.quiz_id = "+quizId;

        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Map<Integer , Question> questions = getQuestionsFromResultSet(resultSet);
        MySQLConnector.closeConnection();

        if(questions == null)
            return null;
        return questions.values();
    }

    public static Map<Integer , Question> getQuestionsFromResultSet(ResultSet resultSet) {
        Map<Integer , Question> questions = new HashMap<Integer, Question>();
        Question questionTemp = null;

        int
                question_id,
                quiz_id,
                correct_answer_id,
                choice_id;

        String
                question_title,
                answer;

        try {
            while(resultSet.next())
            {
                question_id = resultSet.getInt("question_id");
                quiz_id = resultSet.getInt("quiz_id");
                correct_answer_id = resultSet.getInt("correct_answer_id");
                choice_id = resultSet.getInt("choice_id");

                question_title = resultSet.getString("question_title");
                answer = resultSet.getString("answer");

                questionTemp = questions.get(question_id);
                if(questionTemp == null)
                    questionTemp = new Question(question_id,question_title , new Choice(correct_answer_id),  new Quiz(quiz_id));


                questionTemp.addChoice(new Choice(question_id, answer));
                questions.put(questionTemp.getId(),questionTemp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return questions;
    }

    @Override
    public Question getQuestionByID(int id) {
        MySQLConnector.openConnection();

        String q = "SELECT " +
                "q.id question_id, " +
                "q.question_title, " +
                "q.quiz_id, " +
                "q.correct_answer_id, " +
                "c.id choice_id, " +
                "c.answer " +
                "FROM " +
                this.tableName+" q LEFT JOIN choices c " +
                "ON q.id = c.question_id " +
                "WHERE q.id = "+id;


        Question question = null;

        ResultSet resultSet = MySQLConnector.executeQuery(q);

        Map<Integer , Question> questions = getQuestionsFromResultSet(resultSet);

        MySQLConnector.closeConnection();

        if(questions == null)
            return null;

        if(!questions.values().iterator().hasNext())
            return null;

        return questions.values().iterator().next();
    }

    @Override
    public int addQuestion(Question question) {
        MySQLConnector.openConnection();
        String q = "INSERT INTO "+this.tableName+"(question_title, quiz_id) VALUES ('"+question.getTitle()+"',"+question.getQuiz().getId()+");";

        int id = -1;
        if(MySQLConnector.executeUpdate(q))
            id = MySQLConnector.getIdOfTheLastAddedIn(this.tableName);

        MySQLConnector.closeConnection();
        return id;
    }

    @Override
    public boolean deleteQuestionByID(int id) {
        if(!isExist(id))
            return false;

        MySQLConnector.openConnection();
        String q = "DELETE FROM "+this.tableName+" WHERE id ="+id+";";

        Boolean result = MySQLConnector.executeUpdate(q);
        MySQLConnector.closeConnection();
        return  result;
    }

    @Override
    public boolean updateQuestionByID(Question newQuestion) {
        if(!isExist(newQuestion.getId()))
            return false;

        MySQLConnector.openConnection();
        String q = "UPDATE "+this.tableName+" SET question_title='"+newQuestion.getTitle()+"', quiz_id='"+newQuestion.getQuiz().getId()+"', correct_answer_id="+newQuestion.getCorrectChoice().getId()+" WHERE id ="+newQuestion.getId()+";";
        Boolean result = MySQLConnector.executeUpdate(q);
        MySQLConnector.closeConnection();
        return  result;
    }

    public Map<Integer, Integer> getCorrectAnswerFor(ArrayList<Integer> questionsIds)
    {
        MySQLConnector.openConnection();
        String oring = generateOring("id",questionsIds);
        String q = "SELECT id, correct_answer_id FROM "+this.tableName+" WHERE "+oring;
        System.out.println(q);
        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Map<Integer, Integer> questions = new HashMap<>();

        System.out.println(resultSet);

        int question_id, correct_answer_id;
        try {
            while(resultSet.next())
            {
                question_id = resultSet.getInt("id");
                correct_answer_id = resultSet.getInt("correct_answer_id");

                questions.put(question_id,correct_answer_id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();

            return null;
        }
        MySQLConnector.closeConnection();
        return questions;

    }

    private String generateOring(String key,ArrayList<Integer> values)
    {
        if(values.size()<1)
            return "";

        String result ="";
        for(int i=0;i<values.size();i++)
        {
            result = " " + key + " = " + values.get(i);
            if(i+1<values.size())
                result += " OR ";
        }
        return  result;
    }

    private Boolean isExist(int id)
    {
        Question question = this.getQuestionByID(id);
        if(question == null)
            return false;
        return true;
    }
}
