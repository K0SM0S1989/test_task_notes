package com.krd.TestTaskNotes.services.implementations;

import com.krd.TestTaskNotes.dto.request.NotesRequestDto;
import com.krd.TestTaskNotes.dto.response.NoteResponseByIdDto;
import com.krd.TestTaskNotes.dto.response.NotesResponseByAllDto;
import com.krd.TestTaskNotes.exceptions.BadRequestException;
import com.krd.TestTaskNotes.models.NoteEntity;
import com.krd.TestTaskNotes.repositories.NoteRepository;
import com.krd.TestTaskNotes.services.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.stream.Collectors;

import static com.krd.TestTaskNotes.utils.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public String getAllNotes(Model model) {
        List<NotesResponseByAllDto> notesDtoList = createAllNotesDtoForResponse();
        model.addAttribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST, notesDtoList);
        return INDEX_PAGE;
    }

    @Override
    public String getDetailsNote(Long id, Model model) throws BadRequestException {
        NoteResponseByIdDto noteResponseByIdDto = NoteResponseByIdDto.mapToDto(findNoteById(id));

        model.addAttribute(ATTRIBUTE_BY_ID_RESPONSE, noteResponseByIdDto);
        return DETAILS_PAGE;
    }

    private NoteEntity findNoteById(Long id) throws BadRequestException {
        return noteRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ERROR_GET_BY_ID));
    }

    @Override
    public String addNote(NotesRequestDto notesRequestDto) throws BadRequestException {
        checkRequestByNull(notesRequestDto);
        log.info("Добавление новой заметки");
        noteRepository.save(NotesRequestDto.mapToEntity(notesRequestDto));
        return REDIRECT;
    }

    @Override
    public String getNoteForUpdatePage(Long id, Model model) throws BadRequestException {
        NoteResponseByIdDto noteResponseByIdDto = NoteResponseByIdDto.mapToDto(findNoteById(id));
        model.addAttribute(ATTRIBUTE_FOR_UPDATE, noteResponseByIdDto);
        return EDIT_PAGE;
    }

    @Override
    public String updateNote(Long id, NotesRequestDto notesRequestDto) throws BadRequestException {
        checkRequestByNull(notesRequestDto);

        NoteEntity noteById = findNoteById(id);
        String oldText = noteById.getText();
        String newText = notesRequestDto.getText().trim();
        if (!oldText.equals(newText)) {
            noteById.setText(newText);
            noteById.setChangeDate(new Date());
            noteRepository.save(noteById);
            log.info("Обновлена запись по идентификатору - {}", id);
            log.info("Было - {}, Стало - {}", oldText, newText);
        } else {
            log.info("Изменение текста не требуется");
        }
        return REDIRECT;
    }

    private void checkRequestByNull(NotesRequestDto notesRequestDto) throws BadRequestException {
        Optional.ofNullable(notesRequestDto)
                .orElseThrow(() -> new BadRequestException(ERROR_UPDATE));
    }

    @Override
    public String deleteNote(Long id) throws BadRequestException {
        noteRepository.delete(findNoteById(id));
        log.info("Удалена запись по идентификатору - {}", id);
        return REDIRECT;
    }

    @Override
    public String searchByQuery(String query, Model model) throws BadRequestException {
        checkQueryByNull(query);

        log.info("Подстрока для поиска - '{}'", query);
        List<NotesResponseByAllDto> responseList;
        if (query.isEmpty()) {
            log.info("Запрос от клиента вернул пустое значение, поэтому будут возвращены все записи");
            return getAllNotes(model);
        } else {
            List<NoteEntity> allByQuery = noteRepository.findAllByQuery(query);
            responseList = allByQuery
                    .stream()
                    .map(NotesResponseByAllDto::mapToDto)
                    .collect(Collectors.toList());
            log.info("Найдено {} записей содержащих подстроку '{}'", responseList.size(), query);
            model.addAttribute(ATTRIBUTE_BY_ALL_RESPONSE_LIST, responseList);
            return INDEX_PAGE;
        }
    }

    private void checkQueryByNull(String query) throws BadRequestException {
        if (Objects.isNull(query)) {
            log.error("Подстрока для поиска - 'null'");
            throw new BadRequestException(ERROR_QUERY);
        }
    }

    private List<NotesResponseByAllDto> createAllNotesDtoForResponse() {
        return noteRepository.findAll()
                .stream()
                .map(NotesResponseByAllDto::mapToDto)
                .sorted(Comparator.comparing(NotesResponseByAllDto::getChangeDate).reversed())
                .collect(Collectors.toList());
    }
}
