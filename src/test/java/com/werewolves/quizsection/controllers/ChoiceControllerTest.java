package com.werewolves.quizsection.controllers;

import com.google.gson.Gson;
import com.werewolves.quizsection.entities.Choice;
import com.werewolves.quizsection.entities.Question;
import com.werewolves.quizsection.services.ChoiceService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sound.midi.Track;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChoiceControllerTest {

    private MockMvc mvc;

    @Mock
    private ChoiceService service;

    @InjectMocks
    private ChoiceController controller;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void getAllChoices() throws Exception {
        String uri = "/api/quiz-section/question/5/choice";
        when(service.getAllChoices(5)).thenReturn(
                Stream.of(
                        new Choice(1 , "Orange"),
                        new Choice(2 , "bananaperform"),
                        new Choice(3 , "Apple")
                ).collect(Collectors.toList())
        );

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*" , Matchers.hasSize(3)));
    }

    @Test
    public void getChoiceByID() throws Exception {
        String uri = "/api/quiz-section/choice/1";
        Choice choice = new Choice(1 , "hello");
        when(service.getChoiceByID(1)).thenReturn(choice);

        mvc.perform(
                get(uri)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id" ,Matchers.is(1)))
                .andExpect(jsonPath("$.title" , Matchers.is("hello")));
        verify(service).getChoiceByID(1);
    }

    @Test
    public void addChoice() throws Exception {
        String uri = "/api/quiz-section/question/5/choice";
        Choice choice = new Choice(1 , "hello");
        Gson gson = new Gson();
        String json = gson.toJson(choice);
        when(service.addChoice(5 , choice)).thenReturn(choice.getId());

        mvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteChoiceByID() throws Exception {
        String uri = "/api/quiz-section/choice/1";

        when(service.deleteChoiceByID(1)).thenReturn(true);

        mvc.perform(delete(uri))
                .andExpect(status().isOk());

        verify(service).deleteChoiceByID(1);
    }

    @Test
    public void updateChoiceByID() throws Exception {
        String uri = "/api/quiz-section/choice";
        Choice choice = new Choice(1, "Orange", new Question(1));
        Gson gson = new Gson();
        String json = gson.toJson(choice);
        when(service.updateChoiceByID(choice)).thenReturn(true);

        mvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk());
    }
}