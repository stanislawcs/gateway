package com.example.gateway.clients;

import com.example.lib.dto.create.ItemCreationRequest;
import com.example.lib.dto.create.ItemCreationResponse;
import com.example.lib.dto.read.ItemReadAllResponse;
import com.example.lib.dto.read.ItemResponse;
import com.example.lib.dto.update.ItemUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@FeignClient(value = "item", url = "${url.items}")
public interface ItemClient {

    @GetMapping("/items")
    List<ItemReadAllResponse> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                      @RequestParam(name = "size", defaultValue = "15", required = false) int size,
                                      @RequestParam(name = "sort", required = false) String sort,
                                      @RequestParam(name = "sizes", required = false) String sizes,
                                      @RequestParam(name = "colors", required = false) String colors,
                                      @RequestParam(name = "brands", required = false) String brands,
                                      @RequestParam(name = "price", required = false) BigDecimal price,
                                      @RequestParam(name = "title", required = false) String title);


    @GetMapping("/items/{id}")
    ItemResponse findOne(@PathVariable("id") UUID id);

    @PostMapping("/items")
    ItemCreationResponse create(@RequestBody ItemCreationRequest request);

    @PutMapping("/items/{id}")
    void update(@RequestBody ItemUpdateRequest request, @PathVariable("id") UUID id);

    @DeleteMapping("/items/{id}")
    void delete(@PathVariable("id") UUID id);
}
