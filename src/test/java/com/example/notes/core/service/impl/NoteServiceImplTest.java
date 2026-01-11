package com.example.notes.core.service.impl;

import com.example.notes.core.contract.NoteRepository;
import com.example.notes.core.model.Note;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceImplTest {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteServiceImpl noteService;

    private static final Long ID = 1L;
    private static final String TITLE = "test tile";
    private static final String CONTENT = "test content";
    private static final OffsetDateTime CREATED_AT = OffsetDateTime.now();

    @Test
    void shouldCreateNote() {
        Note note = new Note(ID, TITLE, CONTENT, CREATED_AT);
        when(noteRepository.createNote(ID, note)).thenReturn(note);

        Note createNote = noteService.createNote(ID, note);

        assertEquals(note, createNote);
        Mockito.verify(noteRepository, Mockito.times(1)).createNote(ID, note);
        Mockito.verify(noteRepository, Mockito.times(0)).deleteNote(ID, 2L);
        Mockito.verify(noteRepository, Mockito.times(0)).updateNote(ID, 2L, note);
        Mockito.verify(noteRepository, Mockito.times(0)).getNotes(2L);
    }

    @Test
    void shouldUpdateNote() {

        Note note = new Note(ID, TITLE, CONTENT, CREATED_AT);
        when(noteRepository.updateNote(ID, 2L, note)).thenReturn(note);

        Note updateNote = noteService.updateNote(ID, 2L, note);

        assertEquals(note, updateNote);
        Mockito.verify(noteRepository, Mockito.times(0)).createNote(ID, note);
        Mockito.verify(noteRepository, Mockito.times(0)).deleteNote(ID, 2L);
        Mockito.verify(noteRepository, Mockito.times(1)).updateNote(ID, 2L, note);
        Mockito.verify(noteRepository, Mockito.times(0)).getNotes(2L);
    }

    @Test
    void shouldGetNotes() {

        Note note1 = new Note(ID, TITLE, CONTENT, CREATED_AT);
        Note note2 = new Note(2L, TITLE, CONTENT, CREATED_AT);

        ArrayList<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);

        when(noteRepository.getNotes(2L)).thenReturn(notes);

        List<Note> getNotes = noteService.getNotes(2L);

        assertEquals(notes, getNotes);
        Mockito.verify(noteRepository, Mockito.times(0)).createNote(ID, note1);
        Mockito.verify(noteRepository, Mockito.times(0)).deleteNote(ID, 2L);
        Mockito.verify(noteRepository, Mockito.times(0)).updateNote(ID, 2L, note1);
        Mockito.verify(noteRepository, Mockito.times(1)).getNotes(2L);
    }

    @Test
    void shouldDeleteNote() {

        Note note = new Note(ID, TITLE, CONTENT, CREATED_AT);
        noteService.deleteNote(ID, 2L);

        Mockito.verify(noteRepository, Mockito.times(0)).createNote(ID, note);
        Mockito.verify(noteRepository, Mockito.times(1)).deleteNote(ID, 2L);
        Mockito.verify(noteRepository, Mockito.times(0)).updateNote(ID, 2L, note);
        Mockito.verify(noteRepository, Mockito.times(0)).getNotes(2L);
    }
}
