package io.vicarius.quotaservice.dto;

import java.time.LocalDateTime;

public record User(String id, String firstName, String lastName, LocalDateTime lastLoginTimeUtc) {
}
