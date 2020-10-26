package com.werewolves.quizsection.controllers;

import com.werewolves.quizsection.entities.QuestionAnswerPair;
import com.werewolves.quizsection.entities.Submission;
import com.werewolves.quizsection.services.SubmissionService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("api/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @GetMapping(value = {"","/"})
    public Collection<Submission> getAllSubmissions()
    {
        return submissionService.getAllSubmissions();
    }

    @GetMapping(value = "/{id}")
    public Submission getSubmissionByID(@PathVariable int id)
    {
        return submissionService.getSubmissionByID(id);
    }

    @GetMapping(value = "/quiz/{quizId}")
    public Collection<Submission> getSubmissionsForQuiz(@PathVariable int quizId)
    {
        return submissionService.getSubmissionsForQuiz(quizId);
    }

    @GetMapping(value = "/user/{userId}")
    public Collection<Submission>  getSubmissionsForUser(@PathVariable int userId)
    {
        return submissionService.getSubmissionsForUser(userId);
    }

    @PostMapping(value = "/quiz/{quizId}/user/{userId}")
    public int addSubmission(@PathVariable int quizId, @PathVariable int userId, @RequestBody Collection<QuestionAnswerPair> answersIds)
    {
        return submissionService.addSubmission(userId,quizId,answersIds);
    }
}
