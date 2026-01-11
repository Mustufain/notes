package com.example.notes.core.contract;

import com.example.notes.core.model.User;
import lombok.NonNull;

public interface UserService {

    User loginOrRegister(@NonNull String email, @NonNull String name, @NonNull String issuer, @NonNull String picture);
}
