package com.example.gateway.controllers;

import com.example.gateway.controllers.fallback.FeignUserFallback;
import com.example.gateway.dto.ListUserDTO;
import com.example.gateway.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "users", url = "${users.host}", fallback = FeignUserFallback.class)
public interface FeignUser {

    @GetMapping("/users")
    List<ListUserDTO> getAll(@RequestParam("page") Integer page,
                             @RequestParam("users-per-page") Integer usersPerPage);

    @GetMapping("/users/{id}")
    UserDTO getOneById(@PathVariable("id")Long id);
}