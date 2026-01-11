package com.example.notes.infrastructure.db.repository.impl;

import com.example.notes.core.contract.NoteRepository;
import com.example.notes.core.model.Note;
import com.example.notes.infrastructure.db.jooq.tables.records.NoteRecord;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.notes.infrastructure.db.jooq.tables.Note.NOTE;
import static com.example.notes.infrastructure.db.jooq.tables.User.USER;

@Slf4j
@AllArgsConstructor
@Repository
public class NoteRepositoryImpl implements NoteRepository {
    private final DSLContext dslContext;

    @Override
    public Note createNote(@NonNull Long userId, @NonNull Note note) {
        NoteRecord record = dslContext.insertInto(NOTE)
                .set(NOTE.TITLE, note.getTitle())
                .set(NOTE.CONTENT, note.getContent())
                .set(NOTE.USER_ID, dslContext.select(USER.ID).from(USER).where(USER.ID.eq(userId)))
                .returning()
                .fetchOne();

        return mapToModel(record);
    }

    @Override
    public Note updateNote(@NonNull Long userId, @NonNull Long noteId, @NonNull Note note) {
        NoteRecord record = dslContext.update(NOTE)
                .set(NOTE.TITLE, note.getTitle())
                .set(NOTE.CONTENT, note.getContent())
                .where(NOTE.ID.eq(noteId))
                .and(NOTE.USER_ID.eq(
                        dslContext.select(USER.ID).from(USER).where(USER.ID.eq(userId))
                ))
                .returning()
                .fetchOne();

        return mapToModel(record);
    }

    @Override
    public List<Note> getNotes(@NonNull Long userId) {
        return dslContext.selectFrom(NOTE)
                .where(NOTE.USER_ID.eq(
                        dslContext.select(USER.ID).from(USER).where(USER.ID.eq(userId))
                ))
                .fetch()
                .map(this::mapToModel);
    }

    @Override
    public void deleteNote(@NonNull Long userId, @NonNull Long noteId) {
        dslContext.deleteFrom(NOTE)
                .where(NOTE.ID.eq(noteId))
                .and(NOTE.USER_ID.eq(
                        dslContext.select(USER.ID).from(USER).where(USER.ID.eq(userId))
                ))
                .execute();
    }

    private Note mapToModel(NoteRecord record) {
        if (record == null) {
            return null;
        }
        return Note.builder()
                .id(record.getId())
                .title(record.getTitle())
                .content(record.getContent())
                .createdAt(record.getCreatedAt())
                .build();
    }
}
