package com.example.gateway.controllers;

import com.example.gateway.exceptions.AccessDeniedException;
import com.example.gateway.services.UserService;
import com.example.lib.client.ItemClient;
import com.example.lib.client.UserClient;
import com.example.lib.dto.create.ItemCreationRequest;
import com.example.lib.dto.create.ItemCreationResponse;
import com.example.lib.dto.filter.ItemFilter;
import com.example.lib.dto.read.ItemReadAllResponse;
import com.example.lib.dto.update.ItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.gateway.constants.Constants.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemClient itemClient;
    private final UserClient userClient;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ItemReadAllResponse>> findAll(@RequestParam(value = "page", required = false) Integer page,
                                                             @RequestParam(value = "size", required = false) Integer size,
                                                             @RequestParam(value = "sort", required = false) String sort,
                                                             ItemFilter filter) {
        return new ResponseEntity<>(itemClient.findAll(page, size, sort, filter), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemCreationResponse> create(@RequestHeader(AUTHORIZATION) String authorizationHeader,
                                                       @RequestBody ItemCreationRequest request) {
        String authorities = userClient.getAuthorities(authorizationHeader);
        if (userService.canManipulateItem(userService.splitAuthoritiesByDelimiter(authorities), CREATE_ITEM)) {
            return new ResponseEntity<>(itemClient.create(request), HttpStatus.CREATED);
        }

        throw new AccessDeniedException(ACCESS_DENIED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestHeader(AUTHORIZATION) String authorizationHeader,
                                             @RequestBody ItemUpdateRequest request, @PathVariable("id") UUID id) {
        String authorities = userClient.getAuthorities(authorizationHeader);
        if (userService.canManipulateItem(userService.splitAuthoritiesByDelimiter(authorities), UPDATE_ITEM)) {
            itemClient.update(request, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@RequestHeader(AUTHORIZATION) String authorizationHeader, @PathVariable("id") UUID id) {
        String authorities = userClient.getAuthorities(authorizationHeader);
        if (userService.canManipulateItem(userService.splitAuthoritiesByDelimiter(authorities), DELETE_ITEM)) {
            itemClient.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

}
