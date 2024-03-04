package com.krd.TestTaskNotes.repositories;

import com.krd.TestTaskNotes.models.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    @Query("SELECT ne FROM NoteEntity ne WHERE ne.text ILIKE CONCAT(:query)" +
            " OR ne.text ILIKE CONCAT('%', :query) " +
            "OR ne.text ILIKE CONCAT(:query, '%')" +
            " OR ne.text ILIKE CONCAT('%', :query,'%')" +
            " ORDER BY ne.changeDate DESC")
    List<NoteEntity> findAllByQuery(String query);
}
