package io.vicarius.quotaservice.controller;

import io.vicarius.quotaservice.dto.UserQuota;
import io.vicarius.quotaservice.service.QuotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/quota")
@RequiredArgsConstructor
public class UserQuotaController {

    private final QuotaService quotaService;

    @GetMapping
    public ResponseEntity<List<UserQuota>> getUsersQuota() {
        return ResponseEntity.ok(quotaService.getUsersQuota());
    }

    @PostMapping("/{userId}")
    public void consumeUserQuota(@PathVariable String userId) {
        quotaService.consumeQuota(userId);
    }

}
