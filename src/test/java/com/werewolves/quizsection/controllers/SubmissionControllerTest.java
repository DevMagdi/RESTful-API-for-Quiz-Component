package com.werewolves.quizsection.controllers;

import com.google.gson.Gson;
import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.entities.QuestionAnswerPair;
import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.entities.Submission;
import com.werewolves.quizsection.services.ChoiceService;
import com.werewolves.quizsection.services.SubmissionService;
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
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubmissionControllerTest {

    private MockMvc mvc;

    @Mock
    private SubmissionService service;

    @InjectMocks
    private SubmissionController controller;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllSubmissions() throws Exception {
        String uri = "/api/submission";
        when(service.getAllSubmissions()).thenReturn(
                Stream.of(
                        new Submission(1 ),
                        new Submission(2 ),
                        new Submission(3 )
                ).collect(Collectors.toList())
        );

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" , Matchers.hasSize(3)));
    }

    @Test
    public void getSubmissionByID() throws Exception {
        String uri = "/api/submission/1";
        Submission submission = new Submission(1 , 10 , 10 , new Quiz(1));
        when(service.getSubmissionByID(1)).thenReturn(submission);

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" ,Matchers.is(submission.getId())))
                .andExpect(jsonPath("$.score" , Matchers.is(submission.getScore())))
                .andExpect(jsonPath("$.userId" , Matchers.is(submission.getUserId())));
        verify(service).getSubmissionByID(1);
    }

    @Test
    public void getSubmissionsForQuiz() throws Exception {
        int quizId = 1;
        String uri = "/api/submission/quiz/1";
        when(service.getSubmissionsForQuiz(quizId)).thenReturn(
                Stream.of(
                        new Submission(1 ),
                        new Submission(2 ),
                        new Submission(3 )
                ).collect(Collectors.toList())
        );

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" , Matchers.hasSize(3)));
    }

    @Test
    public void getSubmissionsForUser() throws Exception {
        String uri = "/api/submission/user/1";
        int userId = 1;
        when(service.getSubmissionsForUser(userId)).thenReturn(
                Stream.of(
                        new Submission(1 ),
                        new Submission(2 ),
                        new Submission(3 )
                ).collect(Collectors.toList())
        );

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" , Matchers.hasSize(3)));
    }

    @Test
    public void addSubmission() throws Exception {
        String uri = "/api/submission/quiz/1/user/2";
        int quizId = 1;
        int userId = 2;

        Collection<QuestionAnswerPair> answersIds = new ArrayList<>();
        answersIds.add(new QuestionAnswerPair(1 , 2));
        answersIds.add(new QuestionAnswerPair(2 , 1));
        answersIds.add(new QuestionAnswerPair(3 , 4));

        Submission submission = new Submission(1);
        Gson gson = new Gson();
        String json = gson.toJson(answersIds);
        when(service.addSubmission(userId , quizId ,answersIds)).thenReturn(submission.getId());

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }
}