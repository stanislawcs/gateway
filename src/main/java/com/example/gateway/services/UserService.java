package com.example.gateway.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    public boolean canManipulateItem(List<String> authorities, String specificAuthority) {
        return authorities.stream().anyMatch(specificAuthority::equalsIgnoreCase);
    }

    public List<String> splitAuthoritiesByDelimiter(String authorities) {
        return Arrays.stream(authorities.split(",\\s*")).toList();
    }

}
