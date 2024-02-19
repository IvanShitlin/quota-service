package io.vicarius.quotaservice.service;

import io.vicarius.quotaservice.dto.UserQuota;
import io.vicarius.quotaservice.exception.QuotaExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class provides an implementation of the QuotaManagementService interface.
 * It uses a local concurrent hash map to manage user quotas.
 */
@Service
public class LocalMapQuotaManagementService implements QuotaManagementService {

    @Value("${max-requests-per-user}")
    private int maxRequestsPerUser;

    private final Map<String, Integer> requestCountMap = new ConcurrentHashMap<>();

    @Override
    public void consumeQuotaForUser(String userId) {
        int requestCount = requestCountMap.getOrDefault(userId, 0);
        if (requestCount >= maxRequestsPerUser) {
            throw new QuotaExceededException("User has exceeded the maximum number of requests");
        } else {
            requestCountMap.put(userId, requestCount + 1);
        }
    }

    @Override
    public List<UserQuota> getUsersQuota(List<String> userIds) {
        return userIds.stream()
                .map(userId -> {
                    int requestCount = requestCountMap.getOrDefault(userId, 0);
                    return new UserQuota(userId, maxRequestsPerUser - requestCount);
                })
                .toList();
    }

}
