package com.pakie.ahslocker.student;

import java.util.List;

public interface StudentService {
    List<Student> findAllStudents();
    Student findStudentById(Long id);
    Student saveStudent(Student student);
    void deleteStudentById(Long id);
    //void assignStudentUsername(Long id);
}
