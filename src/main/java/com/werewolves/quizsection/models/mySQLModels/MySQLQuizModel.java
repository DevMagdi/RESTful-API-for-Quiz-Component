package com.werewolves.quizsection.models.mySQLModels;

import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.entities.Skill;
import com.werewolves.quizsection.models.QuizModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("MySQLQuizModel")
public class MySQLQuizModel extends QuizModel {
    private String tableName = "quizes";

    @Override
    public Collection<Quiz> getAllQuizzes() {
        MySQLConnector.openConnection();

        String q =
                "SELECT " +
                        "quiz.id quiz_id, " +
                        "quiz.title quiz_title, " +
                        "quiz.pass_score, " +
                        "quiz.duration, " +
                        "quiz.creator_id, " +
                        "quiz.skill_type_id, " +
                        "skill.name skill_name, " +
                        "question.id question_id, " +
                        "question.question_title, " +
                        "question.correct_answer_id, " +
                        "choice.id choice_id, " +
                        "choice.answer " +
                "FROM " +
                    " "+this.tableName+" quiz " +
                "LEFT JOIN " +
                    "skills skill ON quiz.skill_type_id = skill.id " +
                "LEFT JOIN " +
                    "questions question ON quiz.id = question.quiz_id " +
                "LEFT JOIN " +
                    "choices choice ON question.id = choice.question_id; ";

        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Map<Integer, Quiz> quizzes = getQuizzesFromResultSet(resultSet);
        MySQLConnector.closeConnection();

        if(quizzes == null)
            return null;

        return quizzes.values();
    }


    public static  Map<Integer, Quiz> getQuizzesFromResultSet(ResultSet resultSet) {
        Map<Integer, Question> questionsMap = MySQLQuestionModel.getQuestionsFromResultSet(resultSet);
        Map<Integer, Quiz> quizzesMap = new HashMap<>();
        Quiz quizTemp = null;
        int     quiz_id,
                skill_type_id,
                pass_score,
                creator_id,
                question_id;

        String  quiz_title,
                skill_name;

        float duration;

        try {
            if(!resultSet.first())
                return null;
            do
            {
                quiz_id = resultSet.getInt("quiz_id");
                creator_id = resultSet.getInt("creator_id");
                skill_type_id = resultSet.getInt("skill_type_id");
                pass_score = resultSet.getInt("pass_score");
                question_id = resultSet.getInt("question_id");

                quiz_title = resultSet.getString("quiz_title");
                skill_name = resultSet.getString("skill_name");

                duration = resultSet.getFloat("duration");

                quizTemp = quizzesMap.get(quiz_id);

                if(quizTemp == null)
                    quizTemp = new Quiz(
                            quiz_id,
                            creator_id,
                            quiz_title,
                            pass_score,
                            duration,
                            new Skill(skill_type_id, skill_name));

                if(!quizTemp.hasQuestion(question_id))
                    quizTemp.addQuestion(questionsMap.get(question_id));

                quizzesMap.put(quizTemp.getId(),quizTemp);
            }while (resultSet.next());

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return quizzesMap;
    }
    @Override
    public Quiz getQuizByID(int id) {
        MySQLConnector.openConnection();

        String q =
                "SELECT " +
                        "quiz.id quiz_id, " +
                        "quiz.title quiz_title, " +
                        "quiz.pass_score, " +
                        "quiz.duration, " +
                        "quiz.creator_id, " +
                        "quiz.skill_type_id, " +
                        "skill.name skill_name, " +
                        "question.id question_id, " +
                        "question.question_title, " +
                        "question.correct_answer_id, " +
                        "choice.id choice_id, " +
                        "choice.answer " +
                        "FROM " +
                        " "+this.tableName+" quiz " +
                        "LEFT JOIN " +
                        "skills skill ON quiz.skill_type_id = skill.id " +
                        "LEFT JOIN " +
                        "questions question ON quiz.id = question.quiz_id " +
                        "LEFT JOIN " +
                        "choices choice ON question.id = choice.question_id " +
                        "WHERE quiz.id ="+id+";";

        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Map<Integer, Quiz> quizzes = getQuizzesFromResultSet(resultSet);
        MySQLConnector.closeConnection();

        if(quizzes == null)
            return null;

        if(!quizzes.values().iterator().hasNext())
            return null;

        return quizzes.values().iterator().next();
    }

    @Override
    public Collection<Quiz> getQuizBySkill(int skillId) {

        MySQLConnector.openConnection();

        String q =
                "SELECT " +
                        "quiz.id quiz_id, " +
                        "quiz.title quiz_title, " +
                        "quiz.pass_score, " +
                        "quiz.duration, " +
                        "quiz.creator_id, " +
                        "quiz.skill_type_id, " +
                        "skill.name skill_name, " +
                        "question.id question_id, " +
                        "question.question_title, " +
                        "question.correct_answer_id, " +
                        "choice.id choice_id, " +
                        "choice.answer " +
                        "FROM " +
                        " "+this.tableName+" quiz " +
                        "LEFT JOIN " +
                        "skills skill ON quiz.skill_type_id = skill.id " +
                        "LEFT JOIN " +
                        "questions question ON quiz.id = question.quiz_id " +
                        "LEFT JOIN " +
                        "choices choice ON question.id = choice.question_id " +
                        "WHERE quiz.skill_type_id = "+skillId+" ;";

        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Map<Integer, Quiz> quizzes = getQuizzesFromResultSet(resultSet);
        MySQLConnector.closeConnection();

        if(quizzes == null)
            return null;

        return quizzes.values();
    }

    @Override
    public int addQuiz(Quiz quiz) {

        MySQLConnector.openConnection();
        String q = "INSERT INTO "+this.tableName+
                "(creator_id, skill_type_id, title, pass_score, duration) " +
                "VALUES " +
                "("+quiz.getCreatorId()+", " +
                ""+quiz.getSkill().getId()+", "+
                "'"+quiz.getTitle()+"', "+
                ""+quiz.getPassScore()+", "+
                ""+quiz.getDuration()+" "+
                ");";

        System.out.println(q);

        int id = -1;
        if(MySQLConnector.executeUpdate(q))
            id = MySQLConnector.getIdOfTheLastAddedIn(this.tableName);

        MySQLConnector.closeConnection();
        return id;
    }

    @Override
    public Boolean updateQuiz(Quiz quiz) {
        if(!isExist(quiz.getId()))
            return false;

        MySQLConnector.openConnection();
        String q = "UPDATE "+this.tableName+" SET " +
                "title='"+quiz.getTitle()+"', " +
                "creator_id='"+quiz.getCreatorId()+"', " +
                "skill_type_id="+quiz.getSkill().getId()+", " +
                "pass_score="+quiz.getPassScore()+", " +
                "duration="+quiz.getDuration()+" " +
                "WHERE id ="+quiz.getId()+";";

        System.out.println(q);

        Boolean result = MySQLConnector.executeUpdate(q);
        MySQLConnector.closeConnection();
        return  result;
    }

    @Override
    public Boolean deleteQuiz(int quizId) {
        if(!isExist(quizId))
            return false;

        MySQLConnector.openConnection();
        String q = "DELETE FROM "+this.tableName+" WHERE id ="+quizId+";";

        System.out.println(q);

        Boolean result = MySQLConnector.executeUpdate(q);
        MySQLConnector.closeConnection();
        return  result;
    }

    private Boolean isExist(int id)
    {
        Quiz quiz= this.getQuizByID(id);
        if(quiz == null)
            return false;
        return true;
    }
}
