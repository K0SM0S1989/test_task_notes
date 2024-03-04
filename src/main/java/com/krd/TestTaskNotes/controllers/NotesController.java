package com.krd.TestTaskNotes.controllers;

import com.krd.TestTaskNotes.dto.request.NotesRequestDto;
import com.krd.TestTaskNotes.exceptions.BadRequestException;
import com.krd.TestTaskNotes.services.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.krd.TestTaskNotes.utils.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(API)
public class NotesController {
    private final NoteService noteService;

    @GetMapping("/")
    public String getAllNotes(Model model) {
        return noteService.getAllNotes(model);
    }

    @GetMapping(DETAIL_ADDRESS + VARIABLE)
    public String getDetailsNote(@PathVariable Long id, Model model) throws BadRequestException {
        return noteService.getDetailsNote(id, model);
    }

    @PostMapping(ADD_NOTE_URI)
    public String addNote(@Valid NotesRequestDto notesRequestDto) throws BadRequestException {
        return noteService.addNote(notesRequestDto);
    }

    @GetMapping(ADD_NOTE_URI)
    public String getAddNotePage() {
        return ADD_PAGE;
    }

    @PostMapping(UPDATE_ADDRESS + VARIABLE)
    public String updateNote(@PathVariable Long id, @Valid NotesRequestDto notesRequestDto)
            throws BadRequestException {
        return noteService.updateNote(id, notesRequestDto);
    }

    @GetMapping(UPDATE_ADDRESS + VARIABLE)
    public String updateNote(@PathVariable Long id, Model model) throws BadRequestException {
        return noteService.getNoteForUpdatePage(id, model);
    }

    @PostMapping(DELETE_ADDRESS + VARIABLE)
    public String deleteNote(@PathVariable Long id) throws BadRequestException {
        return noteService.deleteNote(id);
    }

    @GetMapping(SEARCH_ADDRESS)
    public String searchByQuery(@RequestParam String query, Model model) throws BadRequestException {
        return noteService.searchByQuery(query, model);
    }
}
