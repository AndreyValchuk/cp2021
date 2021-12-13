package com.siard.movielibrary.api.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class UserRolesResponse {
    private Collection<String> roles;
}
