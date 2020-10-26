package com.werewolves.quizsection.controllers;

import com.google.gson.Gson;
import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.entities.Skill;
import com.werewolves.quizsection.services.ChoiceService;
import com.werewolves.quizsection.services.SkillService;
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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillControllerTest {

    private MockMvc mvc;

    @Mock
    private SkillService service;

    @InjectMocks
    private SkillController controller;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void addSkill() throws Exception {
        String uri = "/api/skill";
        Skill skill = new Skill(1);
        Gson gson = new Gson();
        String json = gson.toJson(skill);
        when(service.addSkill(skill)).thenReturn(skill.getId());

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void getAllSkills() throws Exception {
        String uri = "/api/skill";
        when(service.getAllSkills()).thenReturn(
                Stream.of(
                        new Skill(1 ),
                        new Skill(1 ),
                        new Skill(1 )
                ).collect(Collectors.toList())
        );

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" , Matchers.hasSize(3)));
    }

    @Test
    public void getSkillByID() throws Exception {
        String uri = "/api/skill/1";
        Skill skill = new Skill(1 , "html");
        when(service.getSkillByID(1)).thenReturn(skill);

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" ,Matchers.is(skill.getId())))
                .andExpect(jsonPath("$.name" , Matchers.is(skill.getName())));
        verify(service).getSkillByID(skill.getId());
    }

    @Test
    public void updateSkill() throws Exception {
        String uri = "/api/skill";
        Skill skill = new Skill(1);
        Gson gson = new Gson();
        String json = gson.toJson(skill);
        when(service.updateSkill(skill)).thenReturn(true);

        mvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteSkill() throws Exception {
        String uri = "/api/skill/1";

        when(service.deleteSkill(1)).thenReturn(true);

        mvc.perform(delete(uri))
                .andExpect(status().isOk());

    }
}