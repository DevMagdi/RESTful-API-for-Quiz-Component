package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.Skill;
import com.werewolves.quizsection.models.SkillModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillServiceTest {

    @Autowired
    private SkillService service;

    @MockBean
    @Qualifier("MySQLSkillModel")
    private SkillModel model;

    @Test
    public void addSkill() {
        Skill skill = new Skill(1 , "problem solving");
        when(model.addSkill(skill)).thenReturn(skill.getId());
        assertEquals(skill.getId() , service.addSkill(skill));
    }

    @Test
    public void getAllSkills() {
        when(model.getAllSkills()).thenReturn(
                Stream.of(
                        new Skill(1 , "problem solving"),
                        new Skill(2 , "programming")
                ).collect(Collectors.toList())
        );

        assertEquals(2 , service.getAllSkills().size());
    }

    @Test
    public void getSkillByID() {
        Skill skill = new Skill(1 , "problem solving");
        when(model.getSkillByID(skill.getId())).thenReturn(skill);

        assertEquals(skill , service.getSkillByID(skill.getId()));
    }

    @Test
    public void updateSkill() {
        Skill skill = new Skill(1 , "problem solving");
        when(model.updateSkill(skill)).thenReturn(true);
        assertTrue(service.updateSkill(skill));
    }

    @Test
    public void deleteSkill() {
        int skillId = 1;
        when(model.deleteSkill(skillId)).thenReturn(true);
        assertTrue(service.deleteSkill(skillId));
    }
}