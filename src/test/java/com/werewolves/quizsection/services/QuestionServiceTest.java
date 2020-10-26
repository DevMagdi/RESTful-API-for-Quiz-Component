package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.models.QuestionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    QuestionService service;

    @MockBean
    @Qualifier("MySQLQuestionModel")
    private QuestionModel model;

    @Test
    public void getAllQuestions() {
        int quizId = 1;
        when(model.getAllQuestions(quizId)).thenReturn(
                Stream.of(new Question(1 , "what" , null ,new Quiz(1)) ,
                        new Question(2 , "where", null , new Quiz(1))
                ).collect(Collectors.toList())
        );
        assertEquals(2 ,service.getAllQuestions(quizId).size());
    }

    @Test
    public void getQuestionByID() {
        int id = 1;
        Question question = new Question(1 , "where" , null , new Quiz(1));
        when(model.getQuestionByID(id)).thenReturn(question);
        assertEquals(question , service.getQuestionByID(id));
    }

    @Test
    public void addQuestion() {
        Question question = new Question(1 , "where" , null , new Quiz(1));
        when(model.addQuestion(question)).thenReturn(question.getId());

        assertEquals(question.getId() , service.addQuestion(question));
    }

    @Test
    public void updateQuestionByID() {
        Question question = new Question(1 , "where" , null , new Quiz(1));
        when(model.updateQuestionByID(question)).thenReturn(true);
        assertTrue(service.updateQuestionByID(question));
    }

    @Test
    public void deleteQuestionByID() {
        int id = 1;
        when(model.deleteQuestionByID(id)).thenReturn(true);
        assertTrue(service.deleteQuestionByID(id));
    }

    @Test
    public void getCorrectAnswerFor() {
        ArrayList<Integer> questionIds = new ArrayList<>();
        questionIds.add(1);
        questionIds.add(2);
        questionIds.add(3);

        Map<Integer , Integer> map = new HashMap<>();
        map.put(1 , 10);
        map.put(2 , 8);
        map.put(3 , 18);

        when(model.getCorrectAnswerFor(questionIds)).thenReturn(map);

        assertEquals(3 , service.getCorrectAnswerFor(questionIds).size());
    }
}