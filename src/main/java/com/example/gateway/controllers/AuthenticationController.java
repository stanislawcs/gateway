package com.example.gateway.controllers;

import com.example.lib.client.UserClient;
import com.example.lib.dto.user.AuthenticationResponse;
import com.example.lib.dto.user.LoginUserDto;
import com.example.lib.dto.user.UserCreationResponse;
import com.example.lib.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.example.gateway.constants.Constants.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserClient userClient;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginUserDto dto) {
        return new ResponseEntity<>(userClient.login(dto), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserCreationResponse> register(@RequestBody UserDto dto) {
        return new ResponseEntity<>(userClient.register(dto), HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<byte[]> refreshToken(@RequestHeader(AUTHORIZATION) String authorizationHeader) throws IOException {
        return userClient.refreshToken(authorizationHeader);
    }

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader(AUTHORIZATION) String authorizationHeader) {
        userClient.logout(authorizationHeader);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
