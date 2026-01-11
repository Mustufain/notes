package com.example.notes.infrastructure.db.repository.impl;

import com.example.notes.core.contract.UserRepository;
import com.example.notes.core.model.User;
import com.example.notes.infrastructure.db.jooq.tables.records.UserRecord;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

import static com.example.notes.infrastructure.db.jooq.tables.User.USER;

@Slf4j
@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dslContext;

    @Override
    public Boolean doesUserExists(@NonNull String email) {
       return dslContext.selectFrom(USER).where(USER.EMAIL.eq(email)).fetchOneInto(Boolean.class);
    }

    @Override
    public User createUser(@NonNull String email,
                           @NonNull String name,
                           @NonNull String issuer,
                           @NonNull String pictureUrl) {
        UserRecord record = dslContext.insertInto(USER)
                .set(USER.EMAIL, email)
                .set(USER.DISPLAY_NAME, name)
                .set(USER.PROVIDER, issuer)
                .set(USER.PICTURE_URL, pictureUrl)
                .set(USER.LAST_LOGIN, OffsetDateTime.now())
                .returning(USER.ID, USER.DISPLAY_NAME, USER.PICTURE_URL)
                .fetchOne();
        return mapToModel(record);
    }

    @Override
    public User updateLastLogin(@NonNull String email) {
        UserRecord record  = dslContext.update(USER)
                .set(USER.LAST_LOGIN, OffsetDateTime.now())
                .where(USER.EMAIL.eq(email))
                .returning(USER.ID, USER.DISPLAY_NAME, USER.PICTURE_URL)
                .fetchOne();
        return mapToModel(record);
    }

    private User mapToModel(UserRecord record) {
        if (record == null) {
            return null;
        }
        return User.builder()
                .id(record.getId())
                .name(record.getDisplayName())
                .picture(record.getPictureUrl())
                .build();
    }
}
