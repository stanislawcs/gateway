package com.example.gateway.controllers;

import com.example.gateway.dto.ListUserDTO;
import com.example.gateway.dto.UserCreationResponse;
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
                                                    @RequestParam(value = "size", required = false) Integer size,
                                                    @RequestParam(value = "sort", required = false) String sort) {
        log.info("UserController: getAll()");
        return new ResponseEntity<>(feignUser.getAll(page, size, sort), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOneById(@PathVariable("id") Long id) {
        log.info("UserController: getOneById()");
        return new ResponseEntity<>(feignUser.getOneById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserCreationResponse> create(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(feignUser.create(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        feignUser.update(userDTO, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        feignUser.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
