package com.example.notes.web.mapper;

import com.example.notes.core.model.User;
import com.example.notes.web.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .picture(user.getPicture())
                .build();
    }
}
