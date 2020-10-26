package com.werewolves.quizsection.models.mySQLModels;

import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.models.ChoiceModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Qualifier("MySQLChoiceModel")
public class MySQLChoiceModel extends ChoiceModel {
    private String tableName = "choices";

//    private static Map<Integer , Choice> choices;
//
//    static {
//        choices = new HashMap<Integer, Choice>(){
//            {
//                put(1 , new Choice(1 , "Muhammed" , 1 , 1));
//                put(2 , new Choice(2 , "Sherif" , 1 , 1));
//                put(3 , new Choice(3 , "Ali" , 2, 1));
//                put(4 , new Choice(4 , "werewolves" , 2, 2));
//            }
//        };
//    }

    @Override
    public Collection<Choice> getAllChoices(int questionId) {

        MySQLConnector.openConnection();
        String q = "SELECT * FROM "+this.tableName+" WHERE question_id="+questionId;
        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Collection<Choice> choices = new ArrayList<>();

        int id, questionID;
        String title;

        try {
            while(resultSet.next())
            {
                id = resultSet.getInt("id");
                title = resultSet.getString("answer");
                questionID = resultSet.getInt("question_id");

                choices.add( new Choice(id,title,new Question(questionID)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();
            return null;
        }
        MySQLConnector.closeConnection();
        return choices;
    }

    @Override
    public Choice getChoiceByID(int id) {
        MySQLConnector.openConnection();

        String q = "SELECT * FROM "+this.tableName+" WHERE id ="+id;
        ResultSet resultSet = MySQLConnector.executeQuery(q);

        int tempID, questionID;
        String title;

        Choice choice = null;

        try {
            if(resultSet.next())
            {
                tempID = resultSet.getInt("id");
                questionID = resultSet.getInt("question_id");
                title = resultSet.getString("answer");
                choice = new Choice(tempID,title, new Question(questionID));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();
            return null;
        }
        MySQLConnector.closeConnection();
        return choice;
    }

    @Override
    public int addChoice(int questionId, Choice choice) {
        MySQLConnector.openConnection();
        String q = "INSERT INTO "+this.tableName+"(answer, question_id) VALUES ('"+choice.getTitle()+"',"+questionId+");";

        int id = -1;
        if(MySQLConnector.executeUpdate(q))
            id = MySQLConnector.getIdOfTheLastAddedIn(this.tableName);

        MySQLConnector.closeConnection();
        return id;
    }

    @Override
    public boolean deleteChoiceByID(int id) {
        if(!isExist(id))
            return false;

        MySQLConnector.openConnection();
        String q = "DELETE FROM "+this.tableName+" WHERE id ="+id+";";

        Boolean result = MySQLConnector.executeUpdate(q);
        MySQLConnector.closeConnection();
        return  result;
    }


    @Override
    public boolean updateChoice(Choice newChoice) {
        if(!isExist(newChoice.getId()))
            return false;

        MySQLConnector.openConnection();
        String q = "UPDATE "+this.tableName+" SET answer='"+newChoice.getTitle()+"', question_id='"+newChoice.getQuestion().getId()+"' WHERE id ="+newChoice.getId()+";";
        Boolean result = MySQLConnector.executeUpdate(q);
        MySQLConnector.closeConnection();
        return  result;

    }

    private Boolean isExist(int id)
    {
        Choice choice = this.getChoiceByID(id);
        if(choice == null)
            return false;
        return true;
    }
}
