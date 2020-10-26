package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.entities.Skill;
import com.werewolves.quizsection.models.QuizModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuizServiceTest {

    @Autowired
    private QuizService service;

    @MockBean
    @Qualifier("MySQLQuizModel")
    private QuizModel model;

    @Test
    public void getAllQuizzes() {
        when(model.getAllQuizzes()).thenReturn(
                Stream.of(
                        new Quiz(1) , new Quiz(2)
                ).collect(Collectors.toList())
        );
        assertEquals(2 , service.getAllQuizzes().size());
    }

    @Test
    public void getQuizByID() {
        Quiz quiz = new Quiz(1);
        when(model.getQuizByID(quiz.getId())).thenReturn(quiz);
        assertEquals(quiz , service.getQuizByID(quiz.getId()));
    }

    @Test
    public void getQuizBySkill() {
        Skill skill = new Skill(1);
        Collection<Quiz> quizzes = new ArrayList<>();
        quizzes.add(new Quiz(1, "programming quiz", 5, 10, skill));
        quizzes.add(new Quiz(1, "IQ quiz", 5, 10, skill));
        when(model.getQuizBySkill(skill.getId())).thenReturn(quizzes);
        assertEquals(2 , service.getQuizBySkill(skill.getId()).size());
    }

    @Test
    public void addQuiz() {
        Quiz quiz = new Quiz(2,1, "programming quiz", 5, 10, new Skill(1));
        when(model.addQuiz(quiz)).thenReturn(quiz.getId());
        assertEquals(quiz.getId() , service.addQuiz(quiz));
    }

    @Test
    public void updateQuiz() {
        Quiz quiz = new Quiz(5,1, "programming quiz", 5, 10, new Skill(1));
        when(model.updateQuiz(quiz)).thenReturn(true);
        assertTrue(service.updateQuiz(quiz));
    }

    @Test
    public void deleteQuiz() {
        Quiz quiz = new Quiz(10,1, "programming quiz", 5, 10, new Skill(1));
        when(model.deleteQuiz(quiz.getId())).thenReturn(true);
        assertTrue(service.deleteQuiz(quiz.getId()));
    }
}