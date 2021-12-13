package com.siard.movielibrary.api.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class ReviewUpdateRequest {
    @Length(min = 100, max = 2000)
    private String text;

    @Min(1)
    @Max(10)
    private Short score;
}
