package io.vicarius.quotaservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserEntity {

    @Id
    private String id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private LocalDateTime lastLoginTimeUtc;

}
