package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.QuestionAnswerPair;
import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.entities.Submission;
import com.werewolves.quizsection.models.SubmissionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubmissionServiceTest {

    @Autowired
    private SubmissionService service;

    @MockBean
    @Qualifier("MySQLSubmissionModel")
    private SubmissionModel model;

    @Test
    public void getAllSubmissions() {
        when(model.getAllSubmissions()).thenReturn(
                Stream.of(
                        new Submission(1, 10, 5, new Quiz(1)),
                        new Submission(3, 20, 10, new Quiz(5))
                ).collect(Collectors.toList())
        );
        assertEquals(2 , service.getAllSubmissions().size());
    }

    @Test
    public void getSubmissionByID() {
        Submission submission = new Submission(13, 5, 46, new Quiz(1));
        when(model.getSubmissionByID(submission.getId())).thenReturn(submission);
        assertEquals(submission , service.getSubmissionByID(submission.getId()));
    }

    @Test
    public void getSubmissionsForQuiz() {
        int quizId = 5;
        when(model.getSubmissionForQuiz(quizId)).thenReturn(
                Stream.of(
                        new Submission(1, 10, 5, new Quiz(1)),
                        new Submission(3, 20, 10, new Quiz(5))
                ).collect(Collectors.toList())
        );

        assertEquals(2 , service.getSubmissionsForQuiz(quizId).size());
    }

    @Test
    public void getSubmissionsForUser() {
        int userId = 3;
        when(model.getSubmissionForUser(userId)).thenReturn(
                Stream.of(
                        new Submission(1, 10, 5, new Quiz(1)),
                        new Submission(3, 20, 10, new Quiz(5))
                ).collect(Collectors.toList())
        );
        assertEquals(2 , service.getSubmissionsForUser(userId).size());
    }


}