package com.example.Library.repository;

import com.example.Library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteUserById(Long id);
    List<User> findByEmail(String email);
    List<User>findByNameContaining(String name);
}
