package com.pakie.ahslocker.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/students")
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    //@Autowired
    //private ParentService parentService;

    //@Autowired
    //private BookedLockerService bookedLockerService;

    //List Students
    @GetMapping("/all")
    public String getAllStudents(Model model) {
        List<Student> students = studentService.findAllStudents();
        model.addAttribute("students", students);
        return "/student/students";
    }

    @GetMapping("/add-student")
    public String addStudent(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/add_student";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/update-student/{id}")
    public String updateStudent(@PathVariable(value = "id") Long id, Model model){
        Student student = studentService.findStudentById(id);
        model.addAttribute("student", student);
        return "/student/update_student";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable(value = "id") Long id, Model model){
        this.studentService.deleteStudentById(id);
        return "redirect:/students";
    }
}
