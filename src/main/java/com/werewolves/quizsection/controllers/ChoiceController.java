package com.werewolves.quizsection.controllers;

import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.services.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/quiz-section/")
public class ChoiceController {

    @Autowired
    private ChoiceService choiceService;

    @GetMapping(value = "hello")
    public String greeting(){
        Collection<Choice> choices = getAllChoices(1);
        return "Hello World";
    }

    @GetMapping(value = "question/{questionId}/choice" )
    public Collection<Choice> getAllChoices(@PathVariable int questionId){
        return this.choiceService.getAllChoices(questionId);
    }

    @GetMapping(value = "choice/{id}")
    public Choice getChoiceByID(@PathVariable int id){
        return this.choiceService.getChoiceByID(id);
    }

    @PostMapping(value = "question/{questionId}/choice")
    public int addChoice(@PathVariable int questionId ,@RequestBody Choice choice){
        return this.choiceService.addChoice(questionId ,choice);
    }

    @DeleteMapping(value = "choice/{id}")
    public boolean deleteChoiceByID(@PathVariable int id){
        return this.choiceService.deleteChoiceByID(id);
    }


    @PutMapping(value = "choice")
    public boolean updateChoiceByID(@RequestBody Choice choice){
        return this.choiceService.updateChoiceByID(choice);
    }


}
