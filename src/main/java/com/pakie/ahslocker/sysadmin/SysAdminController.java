package com.pakie.ahslocker.sysadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SysAdminController {

    @Autowired
    private SysAdminService sysAdminService;

    @GetMapping("/admin") //Admin Dashboard
    public String adminDashboard(Model model){
        return "/sysadmin/admin_dashboard";
    }

    @GetMapping("/sysadmins")
    public String getAllSysAdmins(Model model) {
        List<SysAdmin> sysAdmins = sysAdminService.getAllAdmins();
        model.addAttribute("sysadmins", sysAdmins);
        return "/sysadmin/sysadmins";
    }

    @GetMapping("/sysadmins/add-admin")
    public String addSysAdmin(Model model) {
        SysAdmin sysAdmin = new SysAdmin();
        model.addAttribute("sysAdmin", sysAdmin);
        return "/sysadmin/add_admin";
    }

    @PostMapping("/saveSysAdmin")
    public String saveSysAdmin(@ModelAttribute("sysAdmin") SysAdmin sysAdmin) {
        sysAdminService.saveAdmin(sysAdmin);
        return "redirect:/sysadmins";
    }

    @GetMapping("/sysadmins/update-admin/{id}")
    public String updateSysAdmin(Model model, @PathVariable Long id) {
        SysAdmin sysAdmin = sysAdminService.getAdminById(id);
        model.addAttribute("sysAdmin", sysAdmin);
        return "/sysadmin/update_admin";
    }

    @GetMapping("/sysadmins/delete-admin/{id}")
    public String deleteSysAdmin(@PathVariable Long id) {
        SysAdmin sysAdmin = sysAdminService.getAdminById(id);
        sysAdminService.deleteAdmin(sysAdmin);
        return "redirect:/sysadmins";
    }
}
