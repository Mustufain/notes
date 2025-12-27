package com.example.notes.web.mapper;

import com.example.notes.core.model.Note;
import com.example.notes.web.dto.NoteRequestDto;
import com.example.notes.web.dto.NoteResponseDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class NoteMapper {

    public Note toModel(NoteRequestDto noteDto) {
        return Note.builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .build();
    }
    public NoteResponseDto toResponse(Note note) {
        return NoteResponseDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .build();
    }
    public List<NoteResponseDto> toResponseList(List<Note> notes) {
        if (notes == null) {
            return Collections.emptyList();
        }
        return notes.stream()
                .map(this::toResponse)
                .toList();
    }
}
