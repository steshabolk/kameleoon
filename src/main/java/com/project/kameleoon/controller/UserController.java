package com.project.kameleoon.controller;

import com.project.kameleoon.dto.UserSimpleDto;
import com.project.kameleoon.dto.request.RegisterRequest;
import com.project.kameleoon.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        UserSimpleDto user = userService.createUser(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
