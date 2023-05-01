package com.modern_inf.management.controller.mapper;

import com.modern_inf.management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;

    private String username;

    private String name;

    private Role role;

    private String asanaPersonalAccessToken;

    private String gitlabPersonalAccessToken;

    private String awsAccessKey;

    private String awsAccessSecretKey;
}
