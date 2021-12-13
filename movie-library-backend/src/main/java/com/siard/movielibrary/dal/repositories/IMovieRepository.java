package com.siard.movielibrary.dal.repositories;

import com.siard.movielibrary.dal.entities.Movie;
import com.siard.movielibrary.dal.entities.SlimMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long> {
    Page<SlimMovie> findBy(Pageable pageable);
    Page<SlimMovie> findByTitleContaining(String title, Pageable pageable);
}
