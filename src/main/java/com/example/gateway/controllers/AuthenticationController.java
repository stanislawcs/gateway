package com.example.gateway.controllers;

import com.example.gateway.clients.UserClient;
import com.example.lib.dto.user.AuthenticationResponse;
import com.example.lib.dto.user.LoginUserDto;
import com.example.lib.dto.user.UserCreationResponse;
import com.example.lib.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserClient userClient;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginUserDto dto) {
        return userClient.login(dto);
    }

    @PostMapping("/registration")
    public UserCreationResponse register(@RequestBody UserDto dto) {
        return userClient.register(dto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<byte[]> refreshToken(@RequestHeader("Authorization") String authorizationHeader) throws IOException {
        return userClient.refreshToken(authorizationHeader);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        userClient.logout(authorizationHeader);
    }

}
