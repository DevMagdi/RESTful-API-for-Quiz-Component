package com.werewolves.quizsection.controllers;

import com.google.gson.Gson;
import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.services.QuestionService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionControllerTest {

    private MockMvc mvc;

    @Mock
    private QuestionService service;

    @InjectMocks
    private QuestionController controller;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllQuestions() throws Exception {
        String uri = "/api/quiz-section/quiz/1/question";
        when(service.getAllQuestions(1)).thenReturn(
                Stream.of(
                        new Question(1),
                        new Question(2),
                        new Question(3)
                ).collect(Collectors.toList())
        );

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" , Matchers.hasSize(3)));
    }

    @Test
    public void getQuestionByID() throws Exception {
        String uri = "/api/quiz-section/question/1";
        Question question = new Question(1 ,  "What", new Choice(1), new Quiz (1));
        when(service.getQuestionByID(1)).thenReturn(question);

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" ,Matchers.is(1)))
                .andExpect(jsonPath("$.title" , Matchers.is("What")));
        verify(service).getQuestionByID(1);
    }

    @Test
    public void addQuestion() throws Exception {
        String uri = "/api/quiz-section/question";
        Question question = new Question(1 ,  "What", new Choice(1), new Quiz (1));
        Gson gson = new Gson();
        String json = gson.toJson(question);
        when(service.addQuestion(question)).thenReturn(question.getId());

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteQuestionByID() throws Exception {
        String uri = "/api/quiz-section/question/1";

        when(service.deleteQuestionByID(1)).thenReturn(true);

        mvc.perform(delete(uri))
                .andExpect(status().isOk());

        verify(service).deleteQuestionByID(1);
    }

    @Test
    public void updateQuestionByID() throws Exception {
        String uri = "/api/quiz-section/question";
        Question question = new Question(1 ,  "What", new Choice(1), new Quiz (1));
        Gson gson = new Gson();
        String json = gson.toJson(question);
        when(service.updateQuestionByID(question)).thenReturn(true);

        mvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getCorrectAnswerFor() throws Exception {
        ArrayList<Integer> questionsIds = new ArrayList<>();
        questionsIds.add(1);
        questionsIds.add(2);
        questionsIds.add(3);

        Map<Integer , Integer> map = new HashMap<>();
        map.put(1 , 4);
        map.put(2 , 2);
        map.put(3 , 1);
        Gson gson = new Gson();
        String json = gson.toJson(questionsIds);

        when(service.getCorrectAnswerFor(questionsIds)).thenReturn(map);

        String uri = "/api/quiz-section/question/correctAnswerFor";

        mvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" , Matchers.hasSize(3)));

    }
}