package com.siard.movielibrary.api.controllers;

import com.siard.movielibrary.api.dtos.requests.*;
import com.siard.movielibrary.api.dtos.responses.MovieGetResponse;
import com.siard.movielibrary.api.dtos.responses.MovieResponse;
import com.siard.movielibrary.api.dtos.responses.PagedMoviesResponse;
import com.siard.movielibrary.api.dtos.responses.ReviewResponse;
import com.siard.movielibrary.bll.dtos.*;
import com.siard.movielibrary.bll.exceptions.AlreadyExistsException;
import com.siard.movielibrary.bll.exceptions.ForbiddenException;
import com.siard.movielibrary.bll.exceptions.NotFoundException;
import com.siard.movielibrary.bll.services.IMovieService;
import com.siard.movielibrary.bll.services.IReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final IMovieService movieService;
    private final IReviewService reviewService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<PagedMoviesResponse> getMovies(@RequestParam(required = false) @Size(max = 100) String title,
                                                             PaginationQuery paginationQuery) {
        PaginationFilter paginationFilter = modelMapper.map(paginationQuery, PaginationFilter.class);
        PagedMoviesDto pagedMoviesDto = movieService.getAllMovies(title, paginationFilter);
        PagedMoviesResponse pagedMoviesResponse = modelMapper.map(pagedMoviesDto, PagedMoviesResponse.class);

        return ResponseEntity.ok(pagedMoviesResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieGetResponse> getMovieById(@PathVariable long id) throws NotFoundException {
        MovieDto movieDto = movieService.getMovieById(id);
        MovieGetResponse movieResponse = modelMapper.map(movieDto, MovieGetResponse.class);

        return ResponseEntity.ok(movieResponse);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieCreationRequest movieCreationRequest) {
        MovieCreationDto movieCreationDto = modelMapper.map(movieCreationRequest, MovieCreationDto.class);
        MovieDto movieDto = movieService.createMovie(movieCreationDto);
        MovieResponse movieResponse = modelMapper.map(movieDto, MovieResponse.class);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(movieResponse.getId()).toUri();

        return ResponseEntity.created(location).body(movieResponse);
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@Valid @RequestBody MovieUpdateRequest movieUpdateRequest,
                                                     @PathVariable long id) throws NotFoundException {
        MovieUpdateDto movieUpdateDto = modelMapper.map(movieUpdateRequest, MovieUpdateDto.class);
        movieUpdateDto.setId(id);
        movieService.updateMovie(movieUpdateDto);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_EDITOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable long id) throws NotFoundException {
        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewResponse> createReview(@PathVariable long movieId,
                                                       @RequestBody ReviewCreationRequest reviewCreationRequest) throws AlreadyExistsException, NotFoundException {
        ReviewCreationDto reviewCreationDto = modelMapper.map(reviewCreationRequest, ReviewCreationDto.class);
        reviewCreationDto.setMovieId(movieId);
        UserAuthDetails userDetails = (UserAuthDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reviewCreationDto.setReviewerId(userDetails.getId());

        ReviewDto reviewDto = reviewService.createReview(reviewCreationDto);

        ReviewResponse reviewResponse = modelMapper.map(reviewDto, ReviewResponse.class);

        return ResponseEntity.status(CREATED).body(reviewResponse);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable long reviewId,
                                                       @RequestBody ReviewUpdateRequest reviewUpdateRequest) throws ForbiddenException, NotFoundException {
        ReviewUpdateDto reviewUpdateDto = modelMapper.map(reviewUpdateRequest, ReviewUpdateDto.class);
        reviewUpdateDto.setId(reviewId);
        UserAuthDetails userDetails = (UserAuthDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        reviewService.updateReview(reviewUpdateDto, userDetails.getId());

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable long reviewId) throws ForbiddenException, NotFoundException {
        UserAuthDetails userDetails = (UserAuthDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        reviewService.deleteReview(reviewId, userDetails);

        return ResponseEntity.noContent().build();
    }
}
