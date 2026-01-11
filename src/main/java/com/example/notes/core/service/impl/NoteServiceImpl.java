package com.example.notes.core.service.impl;

import com.example.notes.core.model.Note;
import com.example.notes.core.contract.NoteService;
import com.example.notes.core.contract.NoteRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Override
    public Note createNote(@NonNull Long userId, @NonNull Note note) {
        return noteRepository.createNote(userId, note);
    }

    @Override
    public Note updateNote(@NonNull Long userId, @NonNull Long noteId, @NonNull Note note) {
        return noteRepository.updateNote(userId, noteId, note);
    }

    @Override
    public List<Note> getNotes(@NonNull Long userId) {
        return noteRepository.getNotes(userId);
    }

    @Override
    public void deleteNote(@NonNull Long userId, @NonNull Long noteId) {
        noteRepository.deleteNote(userId, noteId);
    }
}
