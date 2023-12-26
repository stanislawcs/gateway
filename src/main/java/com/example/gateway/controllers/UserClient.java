package com.example.gateway.controllers;

import com.example.gateway.dto.ListUserDto;
import com.example.gateway.dto.UserCreationResponse;
import com.example.gateway.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile(value = "local")
@FeignClient(value = "users", url = "${users.host}")
public interface UserClient {

    @GetMapping("/users")
    List<ListUserDto> getAll(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "size", required = false) Integer size,
                             @RequestParam(value = "sort", required = false) String sort);

    @GetMapping("/users/{id}")
    UserDto getById(@PathVariable("id") Long id);

    @PostMapping("/users")
    UserCreationResponse create(@RequestBody UserDto userDTO);

    @PutMapping("/users/{id}")
    void update(@RequestBody UserDto userDTO, @PathVariable("id") Long id);

    @DeleteMapping("/users/{id}")
    void delete(@PathVariable("id") Long id);

}
