package com.example.gateway.controllers;

import com.example.gateway.dto.ListUserDto;
import com.example.gateway.dto.UserCreationResponse;
import com.example.gateway.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final FeignClientUser feignClientUser;

    @GetMapping
    public ResponseEntity<List<ListUserDto>> getAll(@RequestParam(value = "page", required = false) Integer page,
                                                    @RequestParam(value = "size", required = false) Integer size,
                                                    @RequestParam(value = "sort", required = false) String sort) {
        log.info("UserController: getAll()");
        return new ResponseEntity<>(feignClientUser.getAll(page, size, sort), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOneById(@PathVariable("id") Long id) {
        log.info("UserController: getById()");
        return new ResponseEntity<>(feignClientUser.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserCreationResponse> create(@RequestBody UserDto userDTO) {
        return new ResponseEntity<>(feignClientUser.create(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id, @RequestBody UserDto userDTO) {
        feignClientUser.update(userDTO, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        feignClientUser.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
