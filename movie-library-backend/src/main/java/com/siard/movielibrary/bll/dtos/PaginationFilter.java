package com.siard.movielibrary.bll.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationFilter {
    private Integer pageNumber;
    private Integer pageSize;
}
