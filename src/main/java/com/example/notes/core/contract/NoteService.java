package com.example.notes.core.contract;

import com.example.notes.core.model.Note;
import lombok.NonNull;

import java.util.List;

public interface NoteService {

    Note createNote(@NonNull String userId, @NonNull Note note);
    Note updateNote(@NonNull String userId, @NonNull Long noteId, @NonNull Note note);
    List<Note> getNotes(@NonNull String userId);
    void deleteNote(@NonNull String userId, @NonNull Long noteId);
}
