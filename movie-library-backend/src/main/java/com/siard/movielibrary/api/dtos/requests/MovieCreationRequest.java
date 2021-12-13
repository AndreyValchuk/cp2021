package com.siard.movielibrary.api.dtos.requests;

import com.siard.movielibrary.api.constraints.MovieReleaseYear;
import com.siard.movielibrary.api.constraints.UniqueStrings;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
public class MovieCreationRequest {
    @NotBlank
    @Size(min = 1, max = 100)
    private String title;

    @MovieReleaseYear
    private Short releaseYear;

    @NotBlank
    @Size(min = 1, max = 50)
    private String country;

    @Size(max = 1000)
    private String description;

    @Size(min = 1, max = 10)
    @UniqueStrings
    private Collection<@Size(min = 1, max = 30) String> genres;
}
