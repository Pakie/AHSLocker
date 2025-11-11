package com.pakie.ahslocker.student;

import com.pakie.ahslocker.user.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public List<Student> findAllStudents() {
        return studentRepo.findAll();
    }

    @Override
    public Student findStudentById(Long id) {
        Optional<Student> optional = studentRepo.findById(id);
        Student student = null;
        if(optional.isPresent()){
            student = optional.get();
        } else {
            throw new RuntimeException("Student with id " + id + " not found");
        }
        return student;
    }

    @Override
    public Student saveStudent(Student student) {
        this.studentRepo.save(student);
        return student;
    }

    @Override
    public void deleteStudentById(Long id) {
        this.studentRepo.deleteById(id);
    }

    /*@Override
    public void assignStudentUsername(Long id) {

    }*/
}
