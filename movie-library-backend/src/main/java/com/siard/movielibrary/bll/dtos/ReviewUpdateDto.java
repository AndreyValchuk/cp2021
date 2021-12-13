package com.siard.movielibrary.bll.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateDto {
    private Long id;
    private String text;
    private Short score;
}
