package com.siard.movielibrary.bll.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class MovieUpdateDto {
    private Long id;
    private String title;
    private Short releaseYear;
    private String country;
    private String description;
    private Collection<String> genres;
}
