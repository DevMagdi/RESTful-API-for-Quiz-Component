package com.werewolves.quizsection.controllers;

import com.werewolves.quizsection.entities.Skill;
import com.werewolves.quizsection.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/skill")
public class SkillController {
    @Autowired
    private SkillService skillService;

    @PostMapping(value = {"", "/"})
    public int addSkill(@RequestBody Skill skill)
    {
        return skillService.addSkill(skill);
    }

    @GetMapping(value = {"", "/"})
    public Collection<Skill> getAllSkills()
    {
        return skillService.getAllSkills();
    }

    @GetMapping(value = {"/{id}"})
    public Skill getSkillByID(@PathVariable int id)
    {
        return skillService.getSkillByID(id);
    }

    @PutMapping(value = {"", "/"})
    public Boolean updateSkill(@RequestBody Skill skill)
    {
        return skillService.updateSkill(skill);
    }

    @DeleteMapping(value = "/{id}")
    public Boolean deleteSkill(@PathVariable int id)
    {
        return skillService.deleteSkill(id);
    }
}
