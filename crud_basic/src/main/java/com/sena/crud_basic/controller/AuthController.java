package com.sena.crud_basic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.crud_basic.DTO.RequestLoginDTO;
import com.sena.crud_basic.DTO.ResponseLogin;
import com.sena.crud_basic.jwt.JwtServices;
import com.sena.crud_basic.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtServices jwtServices;

  @PostMapping("/login")
public ResponseEntity<ResponseLogin> login(@RequestBody RequestLoginDTO request) {
    // Autenticar (sin guardar en variable)
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    
    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    final String token = jwtServices.generateToken(userDetails);
    
    return ResponseEntity.ok(new ResponseLogin(token));
}
    }