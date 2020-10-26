package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.Skill;
import com.werewolves.quizsection.models.SkillModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SkillService {

    @Autowired
    @Qualifier("MySQLSkillModel")
    SkillModel skillModel;

    public int addSkill(Skill skill)
    {
        return skillModel.addSkill(skill);
    }

    public Collection<Skill> getAllSkills()
    {
        return skillModel.getAllSkills();
    }

    public Skill getSkillByID(int id )
    {

        return skillModel.getSkillByID(id);
    }


    public Boolean updateSkill(Skill skill )
    {

        return skillModel.updateSkill(skill);
    }

    public Boolean deleteSkill(int skillId )
    {

        return skillModel.deleteSkill(skillId);
    }
}
