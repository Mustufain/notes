package com.example.notes.infrastructure.db.repository.impl;

import com.example.notes.core.contract.NoteRepository;
import com.example.notes.core.contract.UserRepository;
import com.example.notes.core.model.Note;
import com.example.notes.core.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class NoteRepositoryImplTest {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldCreateNote() {
        User user = userRepository.createUser(
                "testemail_exists@example.com",
                "testName",
                "testIssuer",
                "testPictureUrl"
        );
        Note note = new Note(null, "title", "content", null);
        Note createdNote = noteRepository.createNote(user.getId(), note);

        assertNotNull(createdNote);
        assertEquals("title", createdNote.getTitle());
        assertEquals("content", createdNote.getContent());

    }

    @Test
    public void shouldUpdateNote() {
        User user = userRepository.createUser(
                "testemail_exists@example.com",
                "testName",
                "testIssuer",
                "testPictureUrl"
        );
        Note note = new Note(null, "title", "content", null);
        Note createdNote = noteRepository.createNote(user.getId(), note);
        Note updatedNote = noteRepository.updateNote(user.getId(), createdNote.getId(),
                new Note(null, "updated title", "updated content", OffsetDateTime.now()));

        assertNotNull(updatedNote);
        assertEquals("updated title", updatedNote.getTitle());
        assertEquals("updated content", updatedNote.getContent());

    }

    @Test
    public void shouldDeleteNote() {
        User user = userRepository.createUser(
                "testemail_exists@example.com",
                "testName",
                "testIssuer",
                "testPictureUrl"
        );
        Note note = new Note(null, "title", "content", null);
        Note note2 = new Note(null, "title2", "content2", null);
        Note createdNote = noteRepository.createNote(user.getId(), note);
        Note createdNote2 = noteRepository.createNote(user.getId(), note2);

        noteRepository.deleteNote(user.getId(), createdNote.getId());

        List<Note> notes = noteRepository.getNotes(user.getId());
        assertEquals(1, notes.size());
        assertEquals("title2", createdNote2.getTitle());
        assertEquals("content2", createdNote2.getContent());

    }

}
