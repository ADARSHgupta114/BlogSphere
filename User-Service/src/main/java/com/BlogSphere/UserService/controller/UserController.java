package com.BlogSphere.UserService.controller;


import com.BlogSphere.UserService.entity.User;
import com.BlogSphere.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userservice;
    @PostMapping("/user/sign-up")
    public ResponseEntity<?> saveUser(@RequestBody User user){
       String response= userservice.SaveUser(user);
       return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/author/sign-up")
    public ResponseEntity<?> saveAuthor(@RequestBody User user){
        String response = userservice.SaveAuthor(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
