package com.example.notes.core.contract;

import com.example.notes.core.model.User;
import lombok.NonNull;

public interface UserRepository {

    Boolean doesUserExists(@NonNull String email);
    User createUser(@NonNull String email, @NonNull String name, @NonNull String issuer, @NonNull String pictureUrl);
    User updateLastLogin(@NonNull String email);

}
