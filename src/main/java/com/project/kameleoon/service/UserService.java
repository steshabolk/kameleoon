package com.project.kameleoon.service;

import com.project.kameleoon.dto.UserSimpleDto;
import com.project.kameleoon.dto.request.RegisterRequest;
import com.project.kameleoon.entity.UserEntity;
import com.project.kameleoon.repository.UserRepository;
import com.project.kameleoon.util.DtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final DtoMapper dtoMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(DtoMapper dtoMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.dtoMapper = dtoMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserSimpleDto createUser(RegisterRequest registerRequest) {
        UserEntity user = dtoMapper.convertToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        UserEntity saved = userRepository.save(user);
        log.debug("save new User{id={}}", saved.getId());
        return dtoMapper.convertToUserSimpleDto(saved);
    }
}
