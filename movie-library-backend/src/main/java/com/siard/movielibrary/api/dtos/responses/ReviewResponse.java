package com.siard.movielibrary.api.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
    private Long id;
    private Long reviewerId;
    private Long movieId;
    private String text;
    private Short score;
}
