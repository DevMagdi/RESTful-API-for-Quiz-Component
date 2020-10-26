package com.werewolves.quizsection.controllers;


import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("api/quiz-section/")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "quiz/{quizId}/question")
    public Collection<Question> getAllQuestions(@PathVariable int quizId){
        return this.questionService.getAllQuestions(quizId);
    }

    @GetMapping(value = "question/{id}")
    public Question getQuestionByID(@PathVariable int id) {
        return this.questionService.getQuestionByID(id);

    }

    @PostMapping(value = "question")
    public int addQuestion(@RequestBody Question question){

        return this.questionService.addQuestion(question);
    }

    @DeleteMapping(value = "question/{id}")
    public boolean deleteQuestionByID(@PathVariable int id){

        return this.questionService.deleteQuestionByID(id);
    }

    @PutMapping(value = "question")
    public boolean updateQuestionByID(@RequestBody Question question){
        return this.questionService.updateQuestionByID(question);
    }

    @GetMapping(value = "question/correctAnswerFor")
    public Map<Integer, Integer> getCorrectAnswerFor(@RequestBody ArrayList<Integer> questionsIds){
        return questionService.getCorrectAnswerFor(questionsIds);
    }
}
