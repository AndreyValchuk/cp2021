package com.siard.movielibrary.api.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class MovieGetResponse {
    private Long id;
    private String title;
    private Short releaseYear;
    private String country;
    private String description;
    private Double averageScore;
    private Collection<String> genres;
    private Collection<MovieReviewResponse> reviews;
}
