package com.example.gateway.controllers;

import com.example.lib.client.UserClient;
import com.example.lib.dto.user.AuthenticationResponse;
import com.example.lib.dto.user.LoginUserDto;
import com.example.lib.dto.user.UserCreationResponse;
import com.example.lib.dto.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.example.gateway.constants.Constants.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication endpoints")
public class AuthenticationController {

    private final UserClient userClient;

    @Operation(
            summary = "Login user",
            description = "Log in user. This endpoint available for whole users (authenticated or not)." +
                    "For successful login user have to input username and password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successful log in!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Validation params exception!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Bad credentials!/Connection refused",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Object with fields that needed to successful login",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginUserDto.class)
                    )
            )
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginUserDto dto) {
        return new ResponseEntity<>(userClient.login(dto), HttpStatus.OK);
    }

    @Operation(
            summary = "Register user",
            description = "Register user. This endpoint available for whole users (authenticated or not). " +
                    "For successful registration user have to input username, password, email",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully created!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserCreationResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User already exists!/Validation params exception!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Connection Refused",
                            content = @Content(
                                    mediaType = "application/json")
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Object with fields that needed to register user",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)
                    )
            )
    )

    @PostMapping("/registration")
    public ResponseEntity<UserCreationResponse> register(@RequestBody UserDto dto) {
        return new ResponseEntity<>(userClient.register(dto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Refresh user tokens",
            description = "Refresh user token (access and refresh).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully refresh user tokens!",
                            content = @Content(
                                    mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized/Invalid token!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Connection Refused",
                            content = @Content(
                                    mediaType = "application/json")
                    )
            }
    )
    @PostMapping("/refresh")
    public ResponseEntity<byte[]> refreshToken(@RequestHeader(AUTHORIZATION) String authorizationHeader) throws IOException {
        return userClient.refreshToken(authorizationHeader);
    }

    @Operation(
            summary = "Logout",
            description = "Logout from system",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully logout from account!",
                            content = @Content(
                                    mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized/Invalid token!",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Connection Refused",
                            content = @Content(
                                    mediaType = "application/json")
                    )
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader(AUTHORIZATION) String authorizationHeader) {
        userClient.logout(authorizationHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
