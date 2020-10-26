package com.werewolves.quizsection.models;

import com.werewolves.quizsection.entities.Choice;
import java.util.Collection;

public abstract class ChoiceModel {

    public abstract Collection<Choice> getAllChoices(int questionId);

    public abstract Choice getChoiceByID(int id);

    public abstract int addChoice(int questionId, Choice choice);

    public abstract boolean deleteChoiceByID(int id);

    public abstract boolean updateChoice(Choice choice);

}
