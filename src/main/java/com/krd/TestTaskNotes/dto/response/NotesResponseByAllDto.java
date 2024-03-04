package com.krd.TestTaskNotes.dto.response;

import com.krd.TestTaskNotes.models.NoteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotesResponseByAllDto {
    private Long id;
    private String title;
    private Date changeDate;

    public static NotesResponseByAllDto mapToDto(NoteEntity noteEntity) {
        String title = noteEntity.getText().length() < 20 ? noteEntity.getText()
                : noteEntity.getText().substring(0, 20);
        return new NotesResponseByAllDto(noteEntity.getId(),
                title,
                noteEntity.getChangeDate());
    }
}
