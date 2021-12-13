package com.siard.movielibrary.bll.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlimMovieDto {
    private Long id;
    private String title;
    private Short releaseYear;
}