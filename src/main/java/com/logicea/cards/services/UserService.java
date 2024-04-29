package com.logicea.cards.services;

import com.logicea.cards.dtos.CardDto;
import com.logicea.cards.exceptions.CustomErrorException;
import com.logicea.cards.exceptions.CustomExtraErrorException;
import com.logicea.cards.models.User;
import com.logicea.cards.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

}