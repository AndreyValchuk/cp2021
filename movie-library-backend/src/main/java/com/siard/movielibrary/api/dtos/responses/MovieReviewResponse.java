package com.siard.movielibrary.api.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieReviewResponse {
    private Long id;
    private Long reviewerId;
    private String reviewerUsername;
    private String text;
    private Short score;
}
