package io.vicarius.quotaservice.repository;

import io.vicarius.quotaservice.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
}
