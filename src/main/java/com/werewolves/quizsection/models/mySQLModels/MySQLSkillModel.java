package com.werewolves.quizsection.models.mySQLModels;

import com.werewolves.quizsection.entities.Skill;
import com.werewolves.quizsection.models.SkillModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@Qualifier("MySQLSkillModel")
public class MySQLSkillModel extends SkillModel {
    private String tableName = "skills";

    @Override
    public int addSkill(Skill skill) {
        MySQLConnector.openConnection();
        String q = "INSERT INTO "+this.tableName+"(name) VALUES ('"+skill.getName()+"');";

        int id = -1;
        if(MySQLConnector.executeUpdate(q))
            id = MySQLConnector.getIdOfTheLastAddedIn(this.tableName);

        MySQLConnector.closeConnection();
        return id;
    }

    @Override
    public Collection<Skill> getAllSkills() {
        MySQLConnector.openConnection();

        String q = "SELECT * FROM "+this.tableName;
        ResultSet resultSet = MySQLConnector.executeQuery(q);
        Collection<Skill> skills = new ArrayList<>();

        int id;
        String skillName;

        try {
            while(resultSet.next())
            {
                id = resultSet.getInt("id");
                skillName = resultSet.getString("name");
                skills.add( new Skill(id,skillName));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();
            return null;
        }
        MySQLConnector.closeConnection();
        return skills;
    }

    @Override
    public Skill getSkillByID(int id) {
        MySQLConnector.openConnection();

        String q = "SELECT * FROM "+this.tableName+" WHERE id ="+id;
        ResultSet resultSet = MySQLConnector.executeQuery(q);

        int tempID;
        String skillName;
        Skill skill = null;

        try {
            if(resultSet.next())
            {
                tempID = resultSet.getInt("id");
                skillName = resultSet.getString("name");
                skill = new Skill(tempID,skillName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            MySQLConnector.closeConnection();
            return null;
        }
        MySQLConnector.closeConnection();
        return skill;
    }

    @Override
    public Boolean updateSkill(Skill skill) {
        if(!isExist(skill.getId()))
            return false;

        MySQLConnector.openConnection();
        String q = "UPDATE "+this.tableName+" SET name='"+skill.getName()+"' WHERE id ="+skill.getId()+";";
        Boolean result = MySQLConnector.executeUpdate(q);
        MySQLConnector.closeConnection();
        return  result;
    }

    @Override
    public Boolean deleteSkill(int id) {
        if(!isExist(id))
            return false;

        MySQLConnector.openConnection();
        String q = "DELETE FROM "+this.tableName+" WHERE id ="+id+";";

        Boolean result = MySQLConnector.executeUpdate(q);
        MySQLConnector.closeConnection();
        return  result;
    }

    private Boolean isExist(int id)
    {
        Skill tempSkill = this.getSkillByID(id);
        if(tempSkill == null)
            return false;
        return true;
    }
}
