package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);
    Optional<User> findById(Long id);
    List<User> findAll();
}