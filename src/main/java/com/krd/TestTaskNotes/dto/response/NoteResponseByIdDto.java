package com.krd.TestTaskNotes.dto.response;

import com.krd.TestTaskNotes.models.NoteEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseByIdDto {
    @Getter
    private Long id;
    @Getter
    private String text;
    private Date publicationDate;
    private Date changeDate;

    public static NoteResponseByIdDto mapToDto(NoteEntity noteEntity) {
        return new NoteResponseByIdDto(noteEntity.getId(),
                noteEntity.getText(),
                noteEntity.getPublicationDate(),
                noteEntity.getChangeDate());
    }

    public String getPublicationDate() {
        return new SimpleDateFormat("dd.MM.yyyy")
                .format(this.publicationDate);
    }

    public String getChangeDate() {
        return new SimpleDateFormat("dd.MM.yyyy")
                .format(this.changeDate);
    }
}
