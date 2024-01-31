package com.example.gateway.services;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service class for user authorities manipulation
 */
@Service
public class UserService {

    /**
     * Checks if the user has a specific authority to manipulate an item.
     *
     * @param authorities         The list of authorities associated with the user.
     * @param specificAuthority   The specific authority to check for.
     * @return {@code true} if the user has the specified authority, {@code false} otherwise.
     */
    public boolean canManipulateItem(List<String> authorities, String specificAuthority) {
        return authorities.stream().anyMatch(specificAuthority::equalsIgnoreCase);
    }

    /**
     * Splits a comma-separated string of authorities into a list of authorities.
     *
     * @param authorities   The comma-separated string of authorities.
     * @return A {@link List} of authorities after splitting the input string.
     */
    public List<String> splitAuthoritiesByDelimiter(String authorities) {
        return Arrays.stream(authorities.split(",\\s*")).toList();
    }

}
