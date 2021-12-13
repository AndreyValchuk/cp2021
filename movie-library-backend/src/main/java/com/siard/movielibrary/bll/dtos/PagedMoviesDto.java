package com.siard.movielibrary.bll.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@Getter
@AllArgsConstructor
public class PagedMoviesDto {
    private Integer pageSize;
    private Integer totalPages;
    private Collection<SlimMovieDto> movies;
}
