package com.siard.movielibrary.dal.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String username;
    private String email;
    private String password;

    @ElementCollection(fetch = EAGER)
    private Collection<String> roles = new ArrayList<>();

    public User(long id) {
        this.id = id;
    }
}