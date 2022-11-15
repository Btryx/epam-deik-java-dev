package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.persistence.User;

import java.util.Objects;

public class UserDto {

    private final String username;
    private final User.Role role;

    public UserDto(String username, User.Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

}
