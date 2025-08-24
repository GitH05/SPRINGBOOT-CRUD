package com.Crud.jqGridCrud.model;

import java.util.List;

public class GridResponse<T> {
    private int page;        // current page
    private int total;       // total pages
    private long records;    // total records
    private List<T> rows;    // list of Student objects

    public GridResponse(int page, int total, long records, List<T> rows) {
        this.page = page;
        this.total = total;
        this.records = records;
        this.rows = rows;
    }
}

