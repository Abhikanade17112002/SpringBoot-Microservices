package com.microsercives.userservice.repositories;

import com.microsercives.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByEmailId(String emailId);
    UserDetails findByEmailId(String username);
}
