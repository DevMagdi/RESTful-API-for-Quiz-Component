package com.werewolves.quizsection.services;

import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.models.ChoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class ChoiceService {
    @Autowired
    @Qualifier("MySQLChoiceModel")
    private ChoiceModel choiceModel;

    public Collection<Choice> getAllChoices(int questionId) {
        Collection<Choice> choices = choiceModel.getAllChoices(questionId);
        return choices;
    }

    public Choice getChoiceByID(int id) {
        Choice choice = choiceModel.getChoiceByID(id);
        if(choice == null){
            /** will be modified **/
            return null;
        }else{
            return choice;
        }
    }

    public boolean updateChoiceByID(Choice choice) {
        if(choiceModel.updateChoice(choice)) {
            return true;
        }
        return false;
    }

    public boolean deleteChoiceByID(int id) {
        if(choiceModel.deleteChoiceByID(id)) {
            return true;
        }
        return false;
    }

    public int addChoice(int questionId, Choice choice) {
        return choiceModel.addChoice(questionId ,choice);
    }

}
