package com.example.gateway.controllers.fallback;

import com.example.gateway.controllers.FeignUser;
import com.example.gateway.dto.ListUserDTO;
import com.example.gateway.dto.UserCreationResponse;
import com.example.gateway.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class FeignUserFallback implements FeignUser {
    @Override
    public List<ListUserDTO> getAll(Integer page, Integer size, String sort) {
        return Collections.emptyList();
    }

    @Override
    public UserDTO getOneById(Long id) {
        return new UserDTO();
    }

    @Override
    public UserCreationResponse create(UserDTO userDTO) {
        return null;
    }

    @Override
    public void update(UserDTO userDTO, Long id) {
        log.info("user not updated");
    }

    @Override
    public void delete(Long id) {
        log.info("User not deleted");
    }
}
