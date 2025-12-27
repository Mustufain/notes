package com.example.notes.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@AllArgsConstructor
@Builder
@Valid
public class NoteRequestDto {
    String title;
    @NotBlank
    String content;
}