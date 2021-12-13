package com.siard.movielibrary.api.dtos.requests;

import lombok.Getter;

@Getter
public class PaginationQuery {
    private int pageNumber = 1;
    private int pageSize = 30;

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber <= 0 ? 1 : pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize <= 0 || pageSize > 100 ? 30 : pageSize;
    }
}
