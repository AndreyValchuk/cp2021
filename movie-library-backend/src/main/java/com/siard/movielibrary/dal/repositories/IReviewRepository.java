package com.siard.movielibrary.dal.repositories;

import com.siard.movielibrary.dal.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {
    Boolean existsByReviewerIdAndMovieId(Long reviewerId, Long movieId);
}
