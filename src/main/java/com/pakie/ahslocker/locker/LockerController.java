package com.pakie.ahslocker.locker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LockerController {

    @Autowired
    private LockerService lockerService;

    @GetMapping("/lockers")
    public String getAllLockers(Model model){
        List<Locker> lockers = lockerService.findAllLockers();
        model.addAttribute("lockers", lockers);
        return "/locker/lockers";
    }

    @GetMapping("/lockers/add-locker")
    public String addLocker(Model model){
        Locker locker = new Locker();
        model.addAttribute("locker", locker);
        return "/locker/add_locker";
    }

    @PostMapping("/saveLocker")
    public String saveLocker(@ModelAttribute("locker") Locker locker, Model model){
        try {
            lockerService.saveLocker(locker);
            return "redirect:/lockers";
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
            // If it's a new locker (no id), go back to add form; otherwise to update form
            return (locker.getId() == null) ? "/locker/add_locker" : "/locker/update_locker";
        }
    }

    @GetMapping("/lockers/update-locker/{id}")
    public String updateLocker(Model model, @PathVariable Long id){
        Locker locker = lockerService.findLockerById(id);
        model.addAttribute("locker", locker);
        return "/locker/update_locker";
    }

    @GetMapping("/lockers/delete-locker/{id}")
    public String deleteLocker(Model model, @PathVariable Long id) {
        this.lockerService.deleteLockerById(id);
        return "redirect:/lockers";
    }

    @GetMapping("/lockers/search")
    public String searchLockers(@ModelAttribute("locker") Locker locker,
                                @ModelAttribute("isAvailable") Boolean isAvailable,
                                Model model) {
        List<Locker> lockers = lockerService.getLockerByGradeAndIsAvailable(locker, isAvailable);
        model.addAttribute("lockers", lockers);
        return "/locker/lockers";
    }
}

