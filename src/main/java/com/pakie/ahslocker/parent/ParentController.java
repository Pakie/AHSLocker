package com.pakie.ahslocker.parent;

import com.pakie.ahslocker.student.Student;
import com.pakie.ahslocker.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ParentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ParentService parentService;

    //@Autowired
    //private BookedLockerService bookedLockerService;

    @GetMapping("/parent")
    public String parentDashboard(Model model){
        return "parent/parent_dashboard";
    }

    @GetMapping("/parents")
    public String getAllParents(Model model){
        List<Parent> parents = parentService.findAllParents();
        model.addAttribute("parents", parents);
        return "/parent/parents";
    }

    @GetMapping("/parents/add-parent")
    public String addParent(Model model){
        Parent parent = new Parent();
        Student student = new Student();
        model.addAttribute("parent", parent);
        model.addAttribute("students", student);
        return "/parent/add_parent";
    }

    @PostMapping("/saveParent")
    public String saveParent(@ModelAttribute("parent") Parent parent){
        parentService.saveParent(parent);
        return "redirect:/parents";
    }

    @GetMapping("/parents/update-parent/{id}")
    public String updateParent(Model model, @PathVariable Long id){
        Parent parent = parentService.findParentById(id);
        model.addAttribute("parent", parent);
        return "/parent/update_parent";
    }

    @GetMapping("/parents/delete-parent/{id}")
    public String deleteParent(Model model, @PathVariable Long id) {
        this.parentService.deleteParentById(id);
        return "redirect:/parents";
    }

    /*@GetMapping("/parents/assignUsername")
    public String assignParentUsername(Long id){
        parentService.assignParentUsername(id);
        return "redirect:/students";
    }*/

}
