package io.vicarius.quotaservice.service;

import io.vicarius.quotaservice.domain.entity.UserEntity;
import io.vicarius.quotaservice.dto.User;
import io.vicarius.quotaservice.dto.UserQuota;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class QuotaService {

    private final UserService userService;

    private final QuotaManagementService quotaManagementService;
    public void consumeQuota(String userId) {
        quotaManagementService.consumeQuotaForUser(userId);
    }

    public List<UserQuota> getUsersQuota() {
        var userIds = userService.getAllUsers()
                .stream()
                .map(User::id)
                .toList();

        return quotaManagementService.getUsersQuota(userIds);
    }
}
