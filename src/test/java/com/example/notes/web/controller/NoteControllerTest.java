package com.example.notes.web.controller;

import com.example.notes.core.contract.NoteService;
import com.example.notes.core.model.Note;
import com.example.notes.web.dto.NoteRequestDto;
import com.example.notes.web.dto.NoteResponseDto;
import com.example.notes.web.exception.GlobalExceptionHandler;
import com.example.notes.web.mapper.NoteMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({NoteController.class, GlobalExceptionHandler.class})
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NoteService service;

    @Autowired
    private NoteMapper mapper;

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        NoteService noteService() {
            return mock(NoteService.class);
        }

        @Bean
        NoteMapper noteMapper() {
            return mock(NoteMapper.class);
        }
    }

    @BeforeEach
    void beforeEach() {
        Mockito.reset(service);
        Mockito.reset(mapper);
    }

    @Test
    void shouldReturnNotes() throws Exception {
        Long userId = 1L;
        Note note = Note.builder().id(1L).title("Title").content("Content").createdAt(OffsetDateTime.now()).build();
        NoteResponseDto responseDto = NoteResponseDto.builder().id(1L).title("Title").content("Content").createdAt(note.getCreatedAt()).build();

        when(service.getNotes(userId)).thenReturn(List.of(note));
        when(mapper.toResponseList(any())).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/v1/users/{userId}/notes", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Title"))
                .andExpect(jsonPath("$[0].content").value("Content"));

        verify(service, times(1)).getNotes(userId);
    }

    @Test
    void shouldCreateNote() throws Exception {
        Long userId = 1L;
        NoteRequestDto requestDto = NoteRequestDto.builder().title("Title").content("Content").build();
        Note note = Note.builder().title("Title").content("Content").build();
        Note createdNote = Note.builder().id(1L).title("Title").content("Content").createdAt(OffsetDateTime.now()).build();
        NoteResponseDto responseDto = NoteResponseDto.builder().id(1L).title("Title").content("Content").createdAt(createdNote.getCreatedAt()).build();

        when(mapper.toModel(any(NoteRequestDto.class))).thenReturn(note);
        when(service.createNote(eq(userId), any(Note.class))).thenReturn(createdNote);
        when(mapper.toResponse(any(Note.class))).thenReturn(responseDto);

        mockMvc.perform(post("/v1/users/{userId}/notes", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"));

        verify(service, times(1)).createNote(eq(userId), any(Note.class));
    }

    @Test
    void shouldUpdateNote() throws Exception {
        Long userId = 1L;
        Long noteId = 2L;
        NoteRequestDto requestDto = NoteRequestDto.builder().title("Updated Title").content("Updated Content").build();
        Note note = Note.builder().title("Updated Title").content("Updated Content").build();
        Note updatedNote = Note.builder().id(noteId).title("Updated Title").content("Updated Content").createdAt(OffsetDateTime.now()).build();
        NoteResponseDto responseDto = NoteResponseDto.builder().id(noteId).title("Updated Title").content("Updated Content").createdAt(updatedNote.getCreatedAt()).build();

        when(mapper.toModel(any(NoteRequestDto.class))).thenReturn(note);
        when(service.updateNote(eq(userId), eq(noteId), any(Note.class))).thenReturn(updatedNote);
        when(mapper.toResponse(any(Note.class))).thenReturn(responseDto);

        mockMvc.perform(put("/v1/users/{userId}/notes/{noteId}", userId, noteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noteId))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"));

        verify(service, times(1)).updateNote(eq(userId), eq(noteId), any(Note.class));
    }

    @Test
    void shouldDeleteNote() throws Exception {
        Long userId = 1L;
        Long noteId = 2L;

        doNothing().when(service).deleteNote(userId, noteId);

        mockMvc.perform(delete("/v1/users/{userId}/notes/{noteId}", userId, noteId))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteNote(userId, noteId);
    }

    @Test
    void shouldReturnBadRequestWhenContentIsBlank() throws Exception {
        Long userId = 1L;
        NoteRequestDto requestDto = NoteRequestDto.builder().title("Title").content("").build();

        mockMvc.perform(post("/v1/users/{userId}/notes", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(service);
    }
}
