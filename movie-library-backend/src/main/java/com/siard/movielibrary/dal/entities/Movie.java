package com.siard.movielibrary.dal.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String title;
    private Short releaseYear;
    private String country;
    private String description;

    @ElementCollection(fetch = EAGER)
    private Set<String> genres;

    @OneToMany(fetch = EAGER)
    @JoinColumn(name="movie_id")
    private Set<Review> reviews;

    public Movie(long id) {
        this.id = id;
    }
}
