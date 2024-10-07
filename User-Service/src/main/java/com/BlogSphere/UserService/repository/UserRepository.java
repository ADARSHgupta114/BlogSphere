package com.BlogSphere.UserService.repository;

import com.BlogSphere.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
