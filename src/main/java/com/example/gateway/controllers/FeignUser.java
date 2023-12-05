package com.example.gateway.controllers;

import com.example.gateway.controllers.fallback.FeignUserFallback;
import com.example.gateway.dto.ListUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "users", url = "${users.host}", fallback = FeignUserFallback.class)
public interface FeignUser {

    @GetMapping("/users")
    List<ListUserDTO> getAll(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "users-per-page", required = false) Integer usersPerPage);
}
