package com.siard.movielibrary.bll.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreationDto {
    private String title;
    private Short releaseYear;
    private String country;
    private String description;
    private Collection<String> genres;
}
