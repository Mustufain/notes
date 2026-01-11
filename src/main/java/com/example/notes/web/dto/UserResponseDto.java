package com.example.notes.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@Builder
public class UserResponseDto {
    Long id;
    String name;
    String picture;

}
