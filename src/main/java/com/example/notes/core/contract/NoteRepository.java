package com.example.notes.core.contract;

import com.example.notes.core.model.Note;
import lombok.NonNull;

import java.util.List;

public interface NoteRepository {

    Note createNote(@NonNull Long userId, @NonNull Note note);
    Note updateNote(@NonNull Long userId, @NonNull Long noteId, @NonNull Note note);
    List<Note> getNotes(@NonNull Long userId);
    void deleteNote(@NonNull Long userId, @NonNull Long noteId);
}
