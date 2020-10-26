package com.werewolves.quizsection.controllers;

import com.google.gson.Gson;
import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.entities.Skill;
import com.werewolves.quizsection.services.ChoiceService;
import com.werewolves.quizsection.services.QuizService;
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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuizControllerTest {

    private MockMvc mvc;

    @Mock
    private QuizService service;

    @InjectMocks
    private QuizController controller;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void getAllQuizzes() throws Exception {
        String uri = "/api/quiz-section/quiz";
        when(service.getAllQuizzes()).thenReturn(
                Stream.of(
                        new Quiz(1 ),
                        new Quiz(2 ),
                        new Quiz(3 )
                ).collect(Collectors.toList())
        );

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" , Matchers.hasSize(3)));
    }

    @Test
    public void getQuizByID() throws Exception {
        String uri = "/api/quiz-section/quiz/1";
        Quiz quiz = new Quiz(1, 5, "IQ", 10, 10, new Skill(1));
        when(service.getQuizByID(1)).thenReturn(quiz);

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" ,Matchers.is(1)))
                .andExpect(jsonPath("$.creatorId" , Matchers.is(5)))
                .andExpect(jsonPath("$.title" , Matchers.is("IQ")));
        verify(service).getQuizByID(1);
    }

    @Test
    public void getQuizzesBySkill() throws Exception {
        String uri = "/api/quiz-section/quiz/skill/1";
        Quiz quiz = new Quiz(1, 5, "IQ", 10, 10, new Skill(1));
        when(service.getQuizBySkill(1)).thenReturn(
                Stream.of(
                        new Quiz(1),
                        new Quiz(2),
                        new Quiz(3)
                ).collect(Collectors.toList())
        );

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" ,Matchers.hasSize(3)));
    }

    @Test
    public void addQuiz() throws Exception {
        String uri = "/api/quiz-section/quiz";
        Quiz quiz = new Quiz(1);
        Gson gson = new Gson();
        String json = gson.toJson(quiz);
        when(service.addQuiz(quiz)).thenReturn(quiz.getId());

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void updateQuiz() throws Exception {
        String uri = "/api/quiz-section/quiz";
        Quiz quiz = new Quiz(1);
        Gson gson = new Gson();
        String json = gson.toJson(quiz);
        when(service.updateQuiz(quiz)).thenReturn(true);

        mvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteQuiz() throws Exception {
        String uri = "/api/quiz-section/quiz/1";

        when(service.deleteQuiz(1)).thenReturn(true);

        mvc.perform(delete(uri))
                .andExpect(status().isOk());

        verify(service).deleteQuiz(1);
    }
}