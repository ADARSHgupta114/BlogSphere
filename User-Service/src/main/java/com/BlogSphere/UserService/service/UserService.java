package com.BlogSphere.UserService.service;

import com.BlogSphere.UserService.entity.User;
import com.BlogSphere.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userrepository;
    public String SaveUser(User user) {
        if(userrepository.existsByEmail(user.getEmail())){
            return "Email Already Exists";
        }
        if(userrepository.existsByUsername(user.getUsername())){
            return "UserName Already Exists";
        }
        String userid = UUID.randomUUID().toString();
        user.setId(userid);
        user.setRole("ROLE_USER");
        userrepository.save(user);
        return "User Saved Successfully";
    }

    public String SaveAuthor(User user) {
        if(userrepository.existsByEmail(user.getEmail())){
            return "Email Already Exists";
        }
        if(userrepository.existsByUsername(user.getUsername())){
            return "UserName Already Exists";
        }
        String userid = UUID.randomUUID().toString();
        user.setId(userid);
        user.setRole("ROLE_AUTHOR");
        userrepository.save(user);
        return "Author Saved Successfully";
    }
}
