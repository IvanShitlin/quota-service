package io.vicarius.quotaservice.service;

import io.vicarius.quotaservice.dto.UserQuota;
import io.vicarius.quotaservice.exception.QuotaExceededException;

import java.util.List;

/**
 * This interface provides methods for managing user quotas.
 */
public interface QuotaManagementService {

    /**
     * Consumes quota for a specific user.
     *
     * @param userId the ID of the user for whom to consume quota
     *
     * @throws QuotaExceededException if the user has exceeded the maximum number of requests
     */
    void consumeQuotaForUser(String userId);

    /**
     * Retrieves the quota information for a list of users.
     *
     * @param userIds a list of user IDs for which to retrieve quota information
     * @return a list of UserQuota objects containing quota information for each user
     */
    List<UserQuota> getUsersQuota(List<String> userIds);
}
