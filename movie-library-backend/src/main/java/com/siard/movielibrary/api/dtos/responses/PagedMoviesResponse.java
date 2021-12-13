package com.siard.movielibrary.api.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class PagedMoviesResponse {
    private Integer pageSize;
    private Integer totalPages;
    private Collection<SlimMovieResponse> movies;
}
