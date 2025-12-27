package com.example.notes.web.controller;

import com.example.notes.core.model.Note;
import com.example.notes.core.contract.NoteService;
import com.example.notes.web.dto.NoteRequestDto;
import com.example.notes.web.dto.NoteResponseDto;
import com.example.notes.web.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users/{userId}/notes")
public class NoteController {
    private final NoteService service;
    private final NoteMapper mapper;


    @GetMapping
    public List<NoteResponseDto> getNotes(@PathVariable("userId") String userId) {
        List<Note> notes = service.getNotes(userId);
        return mapper.toResponseList(notes);
    }

    @PutMapping("/{noteId}")
    public NoteResponseDto updateNote(@PathVariable("userId") String userId,
                              @PathVariable("noteId") Long noteId,
                              @RequestBody NoteRequestDto updateNoteRequest) {
        Note note = mapper.toModel(updateNoteRequest);
        Note updatedNote = service.updateNote(userId, noteId, note);
        return mapper.toResponse(updatedNote);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponseDto createNote(@PathVariable("userId") String userId,
                              @RequestBody NoteRequestDto createNoteRequest) {
        Note note = mapper.toModel(createNoteRequest);
        Note createdNote = service.createNote(userId, note);
        return mapper.toResponse(createdNote);
    }

    @DeleteMapping("/{noteId}")
    public void deleteNote(@PathVariable("userId") String userId,
                           @PathVariable("noteId") Long noteId) {
        service.deleteNote(userId, noteId);
    }
}
