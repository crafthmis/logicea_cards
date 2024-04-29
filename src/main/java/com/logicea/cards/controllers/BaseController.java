package com.logicea.cards.controllers;


import com.logicea.cards.services.CardService;
import com.logicea.cards.services.JwtService;
import com.logicea.cards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class BaseController {

    @Autowired
    protected JwtService jwtService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected UserService userService;

    @Autowired
    protected CardService cardService;

}
