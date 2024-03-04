package com.krd.TestTaskNotes.services.implementations;

import com.krd.TestTaskNotes.LoginTest;
import com.krd.TestTaskNotes.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NoteServiceImplTest extends LoginTest {
    @Mock
    Model model;
    @Autowired
    NoteServiceImpl noteService;

    @Test
    void addNoteTest() {
        assertThrows(BadRequestException.class, () -> noteService.addNote(null));
    }

    @Test
    void updateNoteTest() {
        assertThrows(BadRequestException.class, () -> noteService.updateNote(1L, null));
    }

    @Test
    void searchByQueryTest() {
        assertThrows(BadRequestException.class, () -> noteService.searchByQuery(null, model));
    }
}