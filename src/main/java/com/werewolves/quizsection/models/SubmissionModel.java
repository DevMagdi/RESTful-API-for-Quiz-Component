package com.werewolves.quizsection.models;

import com.werewolves.quizsection.entities.Submission;

import java.util.Collection;

public abstract class SubmissionModel extends Model {
    public abstract Collection<Submission> getAllSubmissions();
    public abstract Submission getSubmissionByID(int id);
    public abstract int addSubmission(Submission submission);
    public abstract Collection<Submission> getSubmissionForQuiz(int quizId);
    public abstract Collection<Submission> getSubmissionForUser(int userId);
}