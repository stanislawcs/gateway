package com.example.gateway.controllers;

import com.example.lib.client.UserClient;
import com.example.lib.dto.user.UserDto;
import com.example.lib.dto.user.UserReadAllDto;
import com.example.lib.dto.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.gateway.constants.Constants.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<List<UserReadAllDto>> getAll(@RequestParam(value = "page", required = false) Integer page,
                                                       @RequestParam(value = "size", required = false) Integer size,
                                                       @RequestParam(value = "sort", required = false) String sort,
                                                       @RequestHeader(AUTHORIZATION) String authorizationHeader) {
        log.info("UserController: getAll()");
        return new ResponseEntity<>(userClient.getAll(page, size, sort, authorizationHeader), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id,
                                                @RequestHeader(AUTHORIZATION) String authorizationHeader) {
        log.info("UserController: getById()");
        return new ResponseEntity<>(userClient.getById(id, authorizationHeader), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") Long id,
                                             @RequestBody @Valid UserDto dto,
                                             @RequestHeader(AUTHORIZATION) String authorizationHeader) {
        userClient.update(id, dto, authorizationHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id,
                                             @RequestHeader(AUTHORIZATION) String authorizationHeader) {
        userClient.delete(id, authorizationHeader);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
