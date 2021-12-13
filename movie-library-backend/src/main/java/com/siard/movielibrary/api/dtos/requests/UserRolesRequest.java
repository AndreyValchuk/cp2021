package com.siard.movielibrary.api.dtos.requests;

import com.siard.movielibrary.api.constraints.UniqueStrings;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
public class UserRolesRequest {
    @Size(max = 10)
    @UniqueStrings
    private Collection<@Size(min = 6, max = 20) String> roles;
}
