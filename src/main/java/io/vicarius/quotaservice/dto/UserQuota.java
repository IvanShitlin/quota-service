package io.vicarius.quotaservice.dto;

public record UserQuota(String userId, Integer remainingQuota) {
}
