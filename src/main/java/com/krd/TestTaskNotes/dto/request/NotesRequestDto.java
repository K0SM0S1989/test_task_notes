package com.krd.TestTaskNotes.dto.request;

import com.krd.TestTaskNotes.models.NoteEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotesRequestDto {
    private String text;

    public static NoteEntity mapToEntity(NotesRequestDto notesDto) {
        return new NoteEntity(null, notesDto.getText().trim(), new Date(), new Date());
    }
}
