package com.example.notes.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import java.time.OffsetDateTime;

@Value
@AllArgsConstructor
@Builder
public class Note {
    Long id;
    String title;
    String content;
    OffsetDateTime createdAt;
}
