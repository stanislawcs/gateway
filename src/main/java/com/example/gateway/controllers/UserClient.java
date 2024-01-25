package com.example.gateway.controllers;

<<<<<<< HEAD
import com.example.lib.dto.user.AuthenticationResponse;
import com.example.lib.dto.user.UserDto;
import com.example.lib.dto.user.UserReadAllDto;
import com.example.lib.dto.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "users", url = "${users.host}")
public interface UserClient {

    @GetMapping("/users")
    List<UserReadAllDto> getAll(@RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "size", required = false) Integer size,
                                @RequestParam(value = "sort", required = false) String sort);

    @GetMapping("/users/{id}")
    UserResponse getById(@PathVariable("id") Long id);

    @PutMapping("/users/{id}")
    AuthenticationResponse update(@PathVariable("id") Long id, @RequestBody UserDto userDTO);

    @DeleteMapping("/users/{id}")
    void delete(@PathVariable("id") Long id);

}
