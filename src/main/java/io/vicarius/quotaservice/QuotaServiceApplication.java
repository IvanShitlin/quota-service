package io.vicarius.quotaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("io.vicarius.quotaservice.repository")
@EnableElasticsearchRepositories("io.vicarius.quotaservice.repository")
public class QuotaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotaServiceApplication.class, args);
    }

}
