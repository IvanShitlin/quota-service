package io.vicarius.quotaservice.service;

import io.vicarius.quotaservice.domain.document.UserDocument;
import io.vicarius.quotaservice.domain.entity.UserEntity;
import io.vicarius.quotaservice.dto.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;

@Component
public class UserMapper {

    public User toDto(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getLastLoginTimeUtc());
    }
    public User toDto(UserDocument userDocument) {
        return new User(userDocument.getId(), userDocument.getFirstName(), userDocument.getLastName(), parseToLocalDateTime(userDocument.getLastLoginTimeUtc()));
    }

    public UserEntity toEntity(User user) {
        return new UserEntity(user.id(), user.firstName(), user.lastName(), user.lastLoginTimeUtc());
    }

    public UserDocument toDocument(User user) {
        return UserDocument.builder()
                .id(user.id())
                .firstName(user.firstName())
                .lastName(user.lastName())
                .lastLoginTimeUtc(parseToTimestamp(user.lastLoginTimeUtc()))
                .build();
    }

    private static LocalDateTime parseToLocalDateTime(Long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId()
        );
    }

    public static Long parseToTimestamp(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(time -> time.toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now())))
                .map(Instant::toEpochMilli)
                .orElse(null);
    }
}
