package com.example.gateway.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ListUserDto {
    private Long id;
    private String username;
    private String email;
}
