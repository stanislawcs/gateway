package com.example.gateway.controllers;

import com.example.gateway.dto.ListUserDTO;
import com.example.gateway.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final FeignUser feignUser;

    @GetMapping
    public ResponseEntity<List<ListUserDTO>> getAll(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "size", required = false) Integer size) {
        log.info("UserController: getAll()");
        return new ResponseEntity<>(feignUser.getAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOneById(@PathVariable("id") Long id) {
        log.info("UserController: getOneById()");
        return new ResponseEntity<>(feignUser.getOneById(id), HttpStatus.OK);
    }

}
