package com.example.gateway.controllers;

import com.example.gateway.clients.ItemClient;
import com.example.gateway.clients.UserClient;
import com.example.gateway.exceptions.AccessDeniedException;
import com.example.gateway.services.UserService;
import com.example.lib.dto.create.ItemCreationRequest;
import com.example.lib.dto.create.ItemCreationResponse;
import com.example.lib.dto.read.ItemReadAllResponse;
import com.example.lib.dto.update.ItemUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public List<ItemReadAllResponse> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                             @RequestParam(name = "size", defaultValue = "15", required = false) int size,
                                             @RequestParam(name = "sort", required = false) String sort,
                                             @RequestParam(name = "sizes", required = false) String sizes,
                                             @RequestParam(name = "colors", required = false) String colors,
                                             @RequestParam(name = "brands", required = false) String brands,
                                             @RequestParam(name = "price", required = false) BigDecimal price,
                                             @RequestParam(name = "title", required = false) String title) {
        return itemClient.findAll(page, size, sort, sizes, colors, brands, price, title);
    }

    @PostMapping
    public ItemCreationResponse create(@RequestHeader(AUTHORIZATION) String authorizationHeader,
                                       @RequestBody ItemCreationRequest request) {
        String authorities = userClient.getAuthorities(authorizationHeader);
        if (userService.canManipulateItem(userService.splitAuthoritiesByDelimiter(authorities), CREATE_ITEM))
            return itemClient.create(request);

        throw new AccessDeniedException(ACCESS_DENIED);
    }

    @PutMapping("/{id}")
    public void update(@RequestHeader(AUTHORIZATION) String authorizationHeader,
                       @RequestBody ItemUpdateRequest request, @PathVariable("id") UUID id) {
        String authorities = userClient.getAuthorities(authorizationHeader);
        if (userService.canManipulateItem(userService.splitAuthoritiesByDelimiter(authorities), UPDATE_ITEM))
            itemClient.update(request, id);
        else
            throw new AccessDeniedException(ACCESS_DENIED);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader(AUTHORIZATION) String authorizationHeader, @PathVariable("id") UUID id) {
        String authorities = userClient.getAuthorities(authorizationHeader);
        if (userService.canManipulateItem(userService.splitAuthoritiesByDelimiter(authorities), DELETE_ITEM))
            itemClient.delete(id);
        else
            throw new AccessDeniedException(ACCESS_DENIED);
    }

}
