package com.werewolves.quizsection.models.mySQLModels;

import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.entities.Submission;
import com.werewolves.quizsection.models.SubmissionModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("MySQLSubmissionModel")
public class MySQLSubmissionModel extends SubmissionModel {
    private String tableName = "submissions";

//    private static Map<Integer , Submission> submissions;
//    private static int counter = 5;
//    static {
//        submissions = new HashMap<Integer, Submission>(){
//            {
//                put(1 , new Submission(1,5,5, new Quiz(3), "2019-04-12 23:59:59"));
//                put(2 , new Submission(2,6,6, new Quiz(3), "2019-04-12 23:59:59"));
//                put(3 , new Submission(3,6,6, new Quiz(3), "2019-04-12 23:59:59"));
//                put(4 , new Submission(4,6,6, new Quiz(3), "2019-04-12 23:59:59"));
//            }
//        };
//    }

    @Override
    public int addSubmission(Submission submission) {
        MySQLConnector.openConnection();
        String q = "INSERT INTO "+this.tableName
                +"(user_id, quiz_id, score, submit_time) " +
                "VALUES ("+
                ""+submission.getUserId()+ ", "+
                ""+submission.getQuiz().getId()+ ", "+
                ""+submission.getScore()+ ", "+
                "'"+submission.getSubmitTime()+ "' "+
                ");";

        int id = -1;
        if(MySQLConnector.executeUpdate(q))
            id = MySQLConnector.getIdOfTheLastAddedIn(this.tableName);

        MySQLConnector.closeConnection();
        return id;
    }

    @Override
    public Collection<Submission> getAllSubmissions() {
        MySQLConnector.openConnection();

        String q = "SELECT * FROM "+this.tableName;
        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Collection<Submission> submissions = new ArrayList<>();

        int     id,
                user_id,
                quiz_id,
                score;
        String submit_time;

        try {
            while(resultSet.next())
            {
                id = resultSet.getInt("id");
                user_id = resultSet.getInt("user_id");
                quiz_id = resultSet.getInt("quiz_id");
                score = resultSet.getInt("score");
                submit_time = resultSet.getString("submit_time");
                submissions.add( new Submission(id,score,user_id,new Quiz(quiz_id),submit_time));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();
            return null;
        }
        MySQLConnector.closeConnection();
        return submissions;
    }

    @Override
    public Submission getSubmissionByID(int id) {
        MySQLConnector.openConnection();

        String q = "SELECT * FROM "+this.tableName+" WHERE id ="+id;
        ResultSet resultSet = MySQLConnector.executeQuery(q);

        int     user_id,
                quiz_id,
                score;
        String submit_time;
        Submission submission = null;

        try {
            if(resultSet.next())
            {
                user_id = resultSet.getInt("user_id");
                quiz_id = resultSet.getInt("quiz_id");
                score = resultSet.getInt("score");
                submit_time = resultSet.getString("submit_time");
                submission = new Submission(id,score,user_id,new Quiz(quiz_id),submit_time);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();
            return null;
        }
        MySQLConnector.closeConnection();
        return submission;
    }

    @Override
    public Collection<Submission> getSubmissionForQuiz(int quizId)
    {
        MySQLConnector.openConnection();

        String q = "SELECT * FROM "+this.tableName+" WHERE quiz_id ="+quizId;
        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Collection<Submission> submissions = new ArrayList<>();

        int     id,
                user_id,
                score;
        String submit_time;

        try {
            while(resultSet.next())
            {
                id = resultSet.getInt("id");
                user_id = resultSet.getInt("user_id");
                score = resultSet.getInt("score");
                submit_time = resultSet.getString("submit_time");
                submissions.add( new Submission(id,score,user_id,new Quiz(quizId),submit_time));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();
            return null;
        }
        MySQLConnector.closeConnection();
        return submissions;
    }

    @Override
    public Collection<Submission> getSubmissionForUser(int userId)
    {
        MySQLConnector.openConnection();

        String q = "SELECT * FROM "+this.tableName+" WHERE user_id ="+userId;
        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Collection<Submission> submissions = new ArrayList<>();

        int     id,
                quiz_id,
                score;
        String submit_time;

        try {
            while(resultSet.next())
            {
                id = resultSet.getInt("id");
                quiz_id = resultSet.getInt("quiz_id");
                score = resultSet.getInt("score");
                submit_time = resultSet.getString("submit_time");
                submissions.add( new Submission(id,score,userId,new Quiz(quiz_id),submit_time));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();
            return null;
        }
        MySQLConnector.closeConnection();
        return submissions;
    }
}
