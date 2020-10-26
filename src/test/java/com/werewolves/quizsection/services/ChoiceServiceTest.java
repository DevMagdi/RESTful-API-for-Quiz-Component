package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.models.ChoiceModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChoiceServiceTest {

    @Autowired
    private ChoiceService service;

    @MockBean
    @Qualifier("MySQLChoiceModel")
    private ChoiceModel model;

    @Test
    public void getAllChoices() {
        int questionId = 1;
        when(model.getAllChoices(questionId)).thenReturn(Stream.of(
                new Choice(1,"apple") , new Choice(2 , "banana")
            ).collect(Collectors.toList())
        );

        Collection<Choice> choices = model.getAllChoices(questionId);
        assertEquals(2 , choices.size());
    }

    @Test
    public void getChoiceByID() {
        int choicId = 1;
        Choice choice = new Choice(1 , "Orange");
        when(model.getChoiceByID(choicId)).thenReturn(choice);

        assertEquals(choice , service.getChoiceByID(choicId));
    }

    @Test
    public void updateChoiceByID() {
        Choice choice = new Choice(1 , "Orange" , new Question(1));
        when(model.updateChoice(choice)).thenReturn(true);
        assertTrue(service.updateChoiceByID(choice));
    }

    @Test
    public void deleteChoiceByID() {
        int id = 1;
        when(model.deleteChoiceByID(id)).thenReturn(true);
        assertTrue(service.deleteChoiceByID(id));
    }

    @Test
    public void addChoice() {
        int questionId = 1;
        Choice choice = new Choice(1 , "Orange" , new Question(1));
        when(model.addChoice(questionId , choice)).thenReturn(choice.getId());

        assertEquals(choice.getId() , service.addChoice(questionId , choice));

    }

}