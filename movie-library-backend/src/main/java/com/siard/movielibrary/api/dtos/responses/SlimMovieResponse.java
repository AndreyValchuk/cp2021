package com.siard.movielibrary.api.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlimMovieResponse {
    private Long id;
    private String title;
    private Short releaseYear;
}
