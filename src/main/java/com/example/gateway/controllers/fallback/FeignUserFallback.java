package com.example.gateway.controllers.fallback;

import com.example.gateway.controllers.FeignUser;
import com.example.gateway.dto.ListUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class FeignUserFallback implements FeignUser {
    @Override
    public List<ListUserDTO> getAll() {
        return Collections.emptyList();
    }
}
