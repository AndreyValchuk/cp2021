package com.siard.movielibrary.bll.services;

import com.siard.movielibrary.bll.dtos.*;
import com.siard.movielibrary.bll.exceptions.NotFoundException;

public interface IMovieService {
    PagedMoviesDto getAllMovies(String title, PaginationFilter paginationFilter);
    MovieDto getMovieById(long id) throws NotFoundException;
    MovieDto createMovie(MovieCreationDto movieCreationDto);
    void updateMovie(MovieUpdateDto movieUpdateDto) throws NotFoundException;
    void deleteMovie(long id) throws NotFoundException;
}