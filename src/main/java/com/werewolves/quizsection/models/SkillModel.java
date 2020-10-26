package com.werewolves.quizsection.models;

import com.werewolves.quizsection.entities.Skill;

import java.util.Collection;

abstract public class SkillModel extends Model {
    public abstract int addSkill(Skill skill);
    public abstract Collection<Skill> getAllSkills();
    public abstract Skill getSkillByID(int id);
    public abstract Boolean updateSkill(Skill skill);
    public abstract Boolean deleteSkill(int id);
}
