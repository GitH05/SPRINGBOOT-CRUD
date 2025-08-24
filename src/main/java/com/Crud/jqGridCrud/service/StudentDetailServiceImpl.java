package com.Crud.jqGridCrud.service;

import com.Crud.jqGridCrud.dao.StudentDetailDao;
import com.Crud.jqGridCrud.model.StudentDetail;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailServiceImpl implements StudentDetailService {

    @Autowired
    private StudentDetailDao studentDetailDao;

    @Override
    public JSONObject getStudentDetail(StudentDetail studentDetail) {
        JSONObject jsonResponse = new JSONObject();
        String query = """
                SELECT id, name, keyName, address, department, IF(active=1, 'Yes', 'No') as active FROM students_detail
                """;
        jsonResponse = studentDetailDao.getStudentDetail(query, studentDetail);
        return jsonResponse;
    }

    @Override
    public JSONObject saveStudentDetail(StudentDetail studentDetail) {
        JSONObject jsonResponse = new JSONObject();
        try {
            JSONObject duplicateJson = new JSONObject(duplicateCheck(studentDetail));
            if (duplicateJson.getBoolean("success")) {
                String query = """
                        INSERT INTO students_detail( name, address, department, keyName, active ) 
                        VALUES (?, ?, ?, ?, ?)
                        """;
                jsonResponse = studentDetailDao.saveStudentDetail(query, studentDetail);
            }else{
            jsonResponse.put("success", false);
            jsonResponse.put("message", duplicateJson.getString("message"));
        }
    }
        catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("Error", true);
            jsonResponse.put ("message", "Unable to update");
        }
         return jsonResponse;
    }

    @Override
    public JSONObject updateStudentDetail(Integer id, StudentDetail studentDetail) {
        JSONObject jsonResponse = new JSONObject();
        try {
            JSONObject duplicateJson = new JSONObject(duplicateCheck(studentDetail));
            if (duplicateJson.getBoolean("success")) {
                String query = """
                        UPDATE students_detail SET name=?, address=?, department=?, active=?, keyName=? WHERE id=?
                        """;
                jsonResponse = studentDetailDao.updateStudentDetail(query, studentDetail);
            }
            else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", duplicateJson.getString("message"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("Error", true);
            jsonResponse.put ("message", "Unable to update");
        }
        return jsonResponse;
    }

    public String duplicateCheck(StudentDetail studentDetail) {
        JSONObject jsonResponse;
        try {
            String query = """
            SELECT COUNT(*) FROM students_detail
            WHERE keyName = ? AND id != ?
        """;
            jsonResponse = studentDetailDao.duplicateCheck(query, studentDetail.getKeyName(), studentDetail.getId());
        } catch (Exception e) {
            jsonResponse = new JSONObject();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error in service: " + e.getMessage());
        }
        return jsonResponse.toString();
    }


    @Override
    public JSONObject deleteStudentDetail(Integer id, StudentDetail studentDetail) {
        JSONObject jsonResponse = new JSONObject();
        try {
            if (studentDetail.getId() > 0) {
                String query = "UPDATE students_detail SET active=? WHERE id=?";
                String isActive = "0".equals(studentDetail.getActive()) ? "Yes" : "No";
                jsonResponse = studentDetailDao.deleteStudentDetail(query, isActive, id);
            }
            else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "id is null");
            }
        }
        catch (Exception e) {
            jsonResponse.put("error",true);
            jsonResponse.put("message","Error in deleting");
        }
        return jsonResponse;
    }
}
