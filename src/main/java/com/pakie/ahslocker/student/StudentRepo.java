package com.pakie.ahslocker.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    Student findByStudentEmail(String studentEmail);
    Student findByStudentNumber(Long studentNumber);
}
