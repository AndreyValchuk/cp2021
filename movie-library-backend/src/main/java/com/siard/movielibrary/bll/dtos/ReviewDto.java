package com.siard.movielibrary.bll.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private Long id;
    private Long reviewerId;
    private Long movieId;
    private String text;
    private Short score;
}
