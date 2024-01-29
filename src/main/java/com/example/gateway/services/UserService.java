package com.example.gateway.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    public boolean canManipulateItem(List<String> authorities, String specificAuthority) {
        for (String authority : authorities) {
            if (specificAuthority.equalsIgnoreCase(authority))
                return true;
        }

        return false;
    }

    public List<String> splitAuthoritiesByDelimiter(String authorities) {
        return Arrays.stream(authorities.split(",\\s*")).toList();
    }

}
