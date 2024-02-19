package io.vicarius.quotaservice.repository;

import io.vicarius.quotaservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
