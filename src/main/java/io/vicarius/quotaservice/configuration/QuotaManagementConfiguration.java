package io.vicarius.quotaservice.configuration;

import io.vicarius.quotaservice.service.LocalMapQuotaManagementService;
import io.vicarius.quotaservice.service.QuotaManagementService;
import io.vicarius.quotaservice.service.QuotaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuotaManagementConfiguration {

    @Bean
    public QuotaManagementService quotaManagementService(LocalMapQuotaManagementService localMapQuotaManagementService) {
        return localMapQuotaManagementService;
    }
}
