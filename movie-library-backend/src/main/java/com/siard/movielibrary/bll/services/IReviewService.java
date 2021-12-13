package com.siard.movielibrary.bll.services;

import com.siard.movielibrary.bll.dtos.ReviewCreationDto;
import com.siard.movielibrary.bll.dtos.ReviewDto;
import com.siard.movielibrary.bll.dtos.ReviewUpdateDto;
import com.siard.movielibrary.bll.dtos.UserAuthDetails;
import com.siard.movielibrary.bll.exceptions.AlreadyExistsException;
import com.siard.movielibrary.bll.exceptions.ForbiddenException;
import com.siard.movielibrary.bll.exceptions.NotFoundException;

public interface IReviewService {
    ReviewDto createReview(ReviewCreationDto reviewCreationDto) throws NotFoundException, AlreadyExistsException;
    void updateReview(ReviewUpdateDto reviewUpdateDto, long userId) throws NotFoundException, ForbiddenException;
    void deleteReview(long reviewId, UserAuthDetails userDetails) throws NotFoundException, ForbiddenException;
}
