package com.krd.TestTaskNotes.controllers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.krd.TestTaskNotes.LoginTest;
import com.krd.TestTaskNotes.dto.request.NotesRequestDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.krd.TestTaskNotes.utils.Constants.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotesControllerTest extends LoginTest {
    private final SimpleDateFormat DTF = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    @Order(1)
    void getAllNotes() throws Exception {
        this.mockMvc.perform(get(API + "/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST, hasSize(1)))
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST,
                        hasItem(
                                allOf(hasProperty("id", is(1L)),
                                        hasProperty("title", is("Первая тестовая запи")))
                        )));
    }

    @Test
    @Order(2)
    void getDetailsNote() throws Exception {
        String expectedDate = DTF.format(new Date());
        this.mockMvc.perform(get(API + DETAIL_ADDRESS + 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_BY_ID_RESPONSE, hasProperty("id", is(1L))))
                .andExpect(model().attribute(ATTRIBUTE_BY_ID_RESPONSE,
                        hasProperty("text", is("Первая тестовая запись в заметках"))))
                .andExpect(model().attribute(ATTRIBUTE_BY_ID_RESPONSE,
                        hasProperty("publicationDate", is(expectedDate))))
                .andExpect(model().attribute(ATTRIBUTE_BY_ID_RESPONSE,
                        hasProperty("changeDate", is(expectedDate))));
    }

    @Test
    @Order(3)
    void getDetailsNoteBadRequest() throws Exception {
        this.mockMvc.perform(get(API + DETAIL_ADDRESS + 2))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @Sql(value = {"classpath:sql/script_after_add.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addNote() throws Exception {
        this.mockMvc.perform(post(API + ADD_NOTE_URI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", "Добавлена новая запись в БД")
        );

        this.mockMvc.perform(get(API + "/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST, hasSize(2)))
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST,
                        hasItem(
                                allOf(hasProperty("id", is(2L)),
                                        hasProperty("title", is("Добавлена новая запи")))
                        )));

    }

    @Test
    @Order(5)
    void getAddNotePage() throws Exception {
        this.mockMvc.perform(get(API + ADD_NOTE_URI))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @Sql(value = {"classpath:sql/script_after_update.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateNoteTest() throws Exception {
        this.mockMvc.perform(post(API + UPDATE_ADDRESS + 1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("text", "Тестовая запись изменена")
        );

        this.mockMvc.perform(get(API + "/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST, hasSize(1)))
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST,
                        hasItem(
                                allOf(hasProperty("id", is(1L)),
                                        hasProperty("title", is("Тестовая запись изме")))
                        )));
    }

    @Test
    @Order(7)
    void updateNoteWrongIdTest() throws Exception {
        NotesRequestDto notesRequestDto = new NotesRequestDto();
        notesRequestDto.setText("Тестовая запись изменена");

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestBody = mapper.writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(notesRequestDto);

        this.mockMvc.perform(post(API + UPDATE_ADDRESS + 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    void updateNotePageTest() throws Exception {
        String expectedDate = DTF.format(new Date());
        this.mockMvc.perform(get(API + UPDATE_ADDRESS + 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_FOR_UPDATE, hasProperty("id", is(1L))))
                .andExpect(model().attribute(ATTRIBUTE_FOR_UPDATE,
                        hasProperty("text", is("Первая тестовая запись в заметках"))))
                .andExpect(model().attribute(ATTRIBUTE_FOR_UPDATE,
                        hasProperty("publicationDate", is(expectedDate))))
                .andExpect(model().attribute(ATTRIBUTE_FOR_UPDATE,
                        hasProperty("changeDate", is(expectedDate))));
    }

    @Test
    @Order(9)
    void updateNotePageWrongIdTest() throws Exception {
        this.mockMvc.perform(get(API + UPDATE_ADDRESS + 2))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(10)
    @Sql(value = {"classpath:sql/script_after_delete.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteNote() throws Exception {
        this.mockMvc.perform(post(API + DELETE_ADDRESS + 1));

        this.mockMvc.perform(get(API + "/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST, hasSize(0)));
    }

    @Test
    @Order(11)
    void deleteNoteWrongId() throws Exception {
        this.mockMvc.perform(post(API + DELETE_ADDRESS + 2))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(12)
    @Sql(value = {"classpath:sql/script_before_search.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void searchByQuery() throws Exception {
        this.mockMvc.perform(get(API + SEARCH_ADDRESS)
                        .param("query", "заметка"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST, hasSize(2)))
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST,
                        hasItem(
                                allOf(hasProperty("id", is(1L)),
                                        hasProperty("title", is("Первая тестовая запи")))
                        )))
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST,
                        hasItem(
                                allOf(hasProperty("id", is(2L)),
                                        hasProperty("title", is("Вторая тестовая запи")))
                        )));

        this.mockMvc.perform(get(API + SEARCH_ADDRESS)
                        .param("query", ""))
                .andExpect(status().isOk())
                .andExpect(model().attribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST, hasSize(4)));
    }
}