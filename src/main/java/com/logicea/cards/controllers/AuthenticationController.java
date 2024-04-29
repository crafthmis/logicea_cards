package com.logicea.cards.controllers;

import com.logicea.cards.dtos.AuthRequestDTO;
import com.logicea.cards.dtos.JwtResponseDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
public class AuthenticationController extends BaseController {

    @PostMapping("/v1/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
            jwtResponseDTO.setAccessToken(jwtService.GenerateToken(authRequestDTO.getUsername()));
            jwtResponseDTO.setExpiryTime(String.valueOf(jwtService.getExpirationTime()));
            return jwtResponseDTO;
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/v1/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
