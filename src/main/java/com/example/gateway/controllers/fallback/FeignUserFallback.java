package com.example.gateway.controllers.fallback;

import com.example.gateway.controllers.FeignUser;
import com.example.gateway.dto.ListUserDTO;
import com.example.gateway.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class FeignUserFallback implements FeignUser {
    @Override
    public List<ListUserDTO> getAll(Integer page, Integer usersPerPage) {
        return Collections.emptyList();
    }

    @Override
    public UserDTO getOneById(Long id) {
        return new UserDTO();
    }
}
