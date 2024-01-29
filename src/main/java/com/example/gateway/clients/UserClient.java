package com.example.gateway.clients;

import com.example.lib.dto.user.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "users", url = "${url.users}")
public interface UserClient {

    @GetMapping("/users")
    List<UserReadAllDto> getAll(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "size", required = false) Integer size,
                                @RequestParam(value = "sort", required = false) String sort,
                                @RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/users/{id}")
    UserResponse getById(@PathVariable("id") Long id,
                         @RequestHeader("Authorization") String authorizationHeader);

    @PutMapping("/users/{id}")
    AuthenticationResponse update(@PathVariable("id") Long id,
                                  @RequestBody UserDto dto,
                                  @RequestHeader("Authorization") String authorizationHeader);

    @DeleteMapping("/users/{id}")
    void delete(@PathVariable("id") Long id,
                @RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("/auth/login")
    AuthenticationResponse login(@RequestBody LoginUserDto dto);

    @PostMapping("/auth/registration")
    UserCreationResponse register(@RequestBody UserDto dto);

    @PostMapping("/auth/refresh")
    ResponseEntity<byte[]> refreshToken(@RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("/auth/logout")
    void logout(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/users/authorities")
    String getAuthorities(@RequestHeader("Authorization") String authorizationHeader);

}
