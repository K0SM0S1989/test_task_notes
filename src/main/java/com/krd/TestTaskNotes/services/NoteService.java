package com.krd.TestTaskNotes.services;

import com.krd.TestTaskNotes.dto.request.NotesRequestDto;
import com.krd.TestTaskNotes.exceptions.BadRequestException;
import org.springframework.ui.Model;

public interface NoteService {
    String getAllNotes(Model model);

    String getDetailsNote(Long id, Model model) throws BadRequestException;

    String addNote(NotesRequestDto notesRequestDto) throws BadRequestException;

    String getNoteForUpdatePage(Long id, Model model) throws BadRequestException;

    String updateNote(Long id, NotesRequestDto notesRequestDto) throws BadRequestException;

    String deleteNote(Long id) throws BadRequestException;

    String searchByQuery(String query, Model model) throws BadRequestException;
}
