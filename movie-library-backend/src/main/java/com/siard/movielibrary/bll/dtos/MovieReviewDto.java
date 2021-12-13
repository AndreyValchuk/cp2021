package com.siard.movielibrary.bll.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieReviewDto {
    private Long id;
    private Long reviewerId;
    private String reviewerUsername;
    private String text;
    private Short score;
}
