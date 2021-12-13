package com.siard.movielibrary.bll.services;

import com.siard.movielibrary.bll.dtos.*;
import com.siard.movielibrary.bll.exceptions.NotFoundException;
import com.siard.movielibrary.dal.entities.Movie;
import com.siard.movielibrary.dal.entities.Review;
import com.siard.movielibrary.dal.entities.SlimMovie;
import com.siard.movielibrary.dal.repositories.IMovieRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService implements IMovieService {
    private final IMovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public PagedMoviesDto getAllMovies(String title, PaginationFilter paginationFilter) {
        Pageable pageable = PageRequest.of(paginationFilter.getPageNumber() - 1, paginationFilter.getPageSize());
        Page<SlimMovie> movies = (title == null || title.isEmpty())
                ? movieRepository.findBy(pageable) : movieRepository.findByTitleContaining(title, pageable);

        return new PagedMoviesDto(movies.getSize(), movies.getTotalPages(),
                modelMapper.map(movies.getContent(), new TypeToken<Collection<SlimMovieDto>>() {}.getType()));
    }

    @Override
    public MovieDto getMovieById(long id) throws NotFoundException {
        Movie movie = movieRepository.findById(id).orElseThrow(NotFoundException::new);

        modelMapper.typeMap(Review.class, MovieReviewDto.class)
                .addMapping(review -> review.getReviewer().getUsername(), MovieReviewDto::setReviewerUsername)
                .addMapping(review -> review.getReviewer().getId(), MovieReviewDto::setReviewerId);
        MovieDto movieDto = modelMapper.map(movie, MovieDto.class);
        double averageScore = movie.getReviews().stream().mapToDouble(Review::getScore).average().orElse(0.00);
        movieDto.setAverageScore(Precision.round(averageScore, 1));

        return movieDto;
    }

    @Override
    public MovieDto createMovie(MovieCreationDto movieCreationDto) {
        Movie movie = modelMapper.map(movieCreationDto, Movie.class);
        movieRepository.save(movie);

        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public void updateMovie(MovieUpdateDto movieUpdateDto) throws NotFoundException {
        Movie movie = entityManager.getReference(Movie.class, movieUpdateDto.getId());

        if (movie == null) {
            throw new NotFoundException();
        }

        modelMapper.map(movieUpdateDto, movie);
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(long id) throws NotFoundException {
        if (!movieRepository.existsById(id)) {
            throw new NotFoundException();
        }

        movieRepository.deleteById(id);
    }
}
