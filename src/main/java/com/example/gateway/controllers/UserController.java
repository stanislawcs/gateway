package com.example.gateway.controllers;

import com.example.gateway.dto.ListUserDTO;
import com.example.gateway.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final FeignUser feignUser;

    @GetMapping("/users")
    public ResponseEntity<List<ListUserDTO>> getAll(@RequestParam("page") Integer page,
                                                    @RequestParam("users-per-page") Integer usersPerPage) {
        log.info("getAll");
        return new ResponseEntity<>(feignUser.getAll(page,usersPerPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getOneById(@PathVariable("id")Long id){
        return new ResponseEntity<>(feignUser.getOneById(id),HttpStatus.OK);
    }


}
