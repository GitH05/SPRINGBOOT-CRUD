package com.Crud.jqGridCrud.service;

import com.Crud.jqGridCrud.model.StudentDetail;
import org.json.JSONObject;

public interface StudentDetailService {
    JSONObject getStudentDetail(StudentDetail studentDetail);

    JSONObject saveStudentDetail(StudentDetail studentDetail);

    JSONObject updateStudentDetail(Integer id, StudentDetail studentDetail);

    JSONObject deleteStudentDetail(Integer id, StudentDetail studentDetail);

    JSONObject getStudentDetailGrid(int page, int rows);
}
