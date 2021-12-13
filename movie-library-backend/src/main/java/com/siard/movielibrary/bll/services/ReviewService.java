package com.siard.movielibrary.bll.services;

import com.siard.movielibrary.bll.dtos.ReviewCreationDto;
import com.siard.movielibrary.bll.dtos.ReviewDto;
import com.siard.movielibrary.bll.dtos.ReviewUpdateDto;
import com.siard.movielibrary.bll.dtos.UserAuthDetails;
import com.siard.movielibrary.bll.exceptions.AlreadyExistsException;
import com.siard.movielibrary.bll.exceptions.ForbiddenException;
import com.siard.movielibrary.bll.exceptions.NotFoundException;
import com.siard.movielibrary.dal.entities.Movie;
import com.siard.movielibrary.dal.entities.Review;
import com.siard.movielibrary.dal.entities.User;
import com.siard.movielibrary.dal.repositories.IMovieRepository;
import com.siard.movielibrary.dal.repositories.IReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService implements IReviewService {
    private final IMovieRepository movieRepository;
    private final IReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Override
    public ReviewDto createReview(ReviewCreationDto reviewCreationDto) throws NotFoundException, AlreadyExistsException {
        if (!movieRepository.existsById(reviewCreationDto.getMovieId())) {
            throw new NotFoundException();
        }

        if (reviewRepository.existsByReviewerIdAndMovieId(reviewCreationDto.getReviewerId(), reviewCreationDto.getMovieId())) {
            throw new AlreadyExistsException();
        }

        Review review = modelMapper.map(reviewCreationDto, Review.class);
        review.setReviewer(new User(reviewCreationDto.getReviewerId()));
        review.setMovie(new Movie(reviewCreationDto.getMovieId()));
        reviewRepository.save(review);

        ReviewDto reviewDto = modelMapper.map(reviewCreationDto, ReviewDto.class);
        reviewDto.setId(review.getId());

        return reviewDto;
    }

    @Override
    public void updateReview(ReviewUpdateDto reviewUpdateDto, long userId) throws NotFoundException, ForbiddenException {
        Review review = reviewRepository.findById(reviewUpdateDto.getId()).orElseThrow(NotFoundException::new);

        if (!Objects.equals(review.getReviewer().getId(), userId)) {
            throw new ForbiddenException();
        }

        modelMapper.map(reviewUpdateDto, review);
    }

    @Override
    public void deleteReview(long reviewId, UserAuthDetails userDetails) throws NotFoundException, ForbiddenException {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundException::new);

        if (!Objects.equals(review.getReviewer().getId(), userDetails.getId()) &&
                userDetails.getAuthorities().stream().noneMatch(authority -> Objects.equals(authority.getAuthority(), "ROLE_MODERATOR"))) {
            throw new ForbiddenException();
        }

        reviewRepository.delete(review);
    }
}
