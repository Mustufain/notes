package com.example.notes.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@AllArgsConstructor
@Builder
public class NoteResponseDto {
    Long id;
    String title;
    String content;
    OffsetDateTime createdAt;
}
