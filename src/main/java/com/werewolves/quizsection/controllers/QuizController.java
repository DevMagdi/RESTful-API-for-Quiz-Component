package com.werewolves.quizsection.controllers;

import com.werewolves.quizsection.entities.Quiz;
import com.werewolves.quizsection.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController


@RequestMapping("/api/quiz-section/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService ;

    @GetMapping (value = {"", "/"})
    public Collection<Quiz> getAllQuizzes()
    {
        return this.quizService.getAllQuizzes() ;
    }

    //Done
    @GetMapping (value = "/{id}")
    public Quiz getQuizByID(@PathVariable  int id)
    {

        return this.quizService.getQuizByID(id) ;
    }

    @GetMapping (value = "/skill/{id}")
    public Collection<Quiz> getQuizzesBySkill(@PathVariable int id)
    {
        return this.quizService.getQuizBySkill(id) ;
    }

    @PostMapping (value = {"" , "/"})
    public int addQuiz( @RequestBody Quiz quiz)
    {
        return this.quizService.addQuiz(quiz) ;
    }

    @PutMapping (value = {"" , "/"})
    public Boolean updateQuiz( @RequestBody Quiz quiz)
    {
        return this.quizService.updateQuiz(quiz) ;
    }

    @DeleteMapping (value = "/{id}")
    public Boolean deleteQuiz(@PathVariable int id)
    {
        return this.quizService.deleteQuiz(id) ;
    }
}
