package com.siard.movielibrary.api.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.siard.movielibrary.api.dtos.requests.UserAuthenticationRequest;
import com.siard.movielibrary.api.dtos.requests.UserCreationRequest;
import com.siard.movielibrary.api.dtos.requests.UserRolesRequest;
import com.siard.movielibrary.api.dtos.requests.UserRolesResponse;
import com.siard.movielibrary.api.dtos.responses.JwtResponse;
import com.siard.movielibrary.bll.dtos.UserCreationDto;
import com.siard.movielibrary.bll.exceptions.AlreadyExistsException;
import com.siard.movielibrary.bll.exceptions.NotFoundException;
import com.siard.movielibrary.bll.services.IUserService;
import com.siard.movielibrary.bll.dtos.UserAuthDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody UserAuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException exception) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        }

        UserAuthDetails authenticatedUser = (UserAuthDetails)authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(getJwt(authenticatedUser)));
    }

    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> createUser(@Valid @RequestBody UserCreationRequest userCreationRequest) throws AlreadyExistsException {
        UserCreationDto userCreationDto = modelMapper.map(userCreationRequest, UserCreationDto.class);
        UserAuthDetails createdUserDto = userService.createUser(userCreationDto);

        return ResponseEntity.status(CREATED).body(new JwtResponse(getJwt(createdUserDto)));
    }

    private String getJwt(UserAuthDetails userAuthDetails) {
        return JWT.create()
                .withSubject(userAuthDetails.getId().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                .withClaim("username", userAuthDetails.getUsername())
                .withClaim("email", userAuthDetails.getEmail())
                .withClaim("roles", userAuthDetails.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).toList())
                .sign(Algorithm.HMAC256("secret"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{username}/roles")
    public ResponseEntity<UserRolesResponse> getUserRoles(@PathVariable String username) throws NotFoundException {
        Collection<String> roles  = userService.getUserRoles(username);

        return ResponseEntity.ok(new UserRolesResponse(roles));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{username}/roles")
    public ResponseEntity<?> updateUserRoles(@PathVariable String username,
                                             @RequestBody UserRolesRequest userRolesRequest)throws NotFoundException {
        userService.updateUserRoles(username, userRolesRequest.getRoles());

        return ResponseEntity.noContent().build();
    }
}