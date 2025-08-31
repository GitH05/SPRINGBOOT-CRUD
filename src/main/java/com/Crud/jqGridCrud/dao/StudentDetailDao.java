package com.Crud.jqGridCrud.dao;

import com.Crud.jqGridCrud.model.StudentDetail;
import org.json.JSONObject;

public interface StudentDetailDao {
    JSONObject getStudentDetail(String query, StudentDetail studentDetail);

    JSONObject saveStudentDetail(String query, StudentDetail studentDetail);

    JSONObject updateStudentDetail(String query, StudentDetail studentDetail);

    JSONObject deleteStudentDetail(String query, String active, Integer id);

    JSONObject duplicateCheck(String query, String keyName, int id);

    int getStudentCount(String countQuery);

    JSONObject getStudentDetailGrid(String query, int offset, int rows);
}
