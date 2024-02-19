package io.vicarius.quotaservice.domain.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Document(indexName = "user")
@Builder
@Getter
@Setter
public class UserDocument {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private Long lastLoginTimeUtc;
}
