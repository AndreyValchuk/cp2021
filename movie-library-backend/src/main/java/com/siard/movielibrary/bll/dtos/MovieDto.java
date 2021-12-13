package com.siard.movielibrary.bll.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class MovieDto {
    private Long id;
    private String title;
    private Short releaseYear;
    private String country;
    private String description;
    private Double averageScore;
    private Collection<String> genres;
    private Collection<MovieReviewDto> reviews;
}
