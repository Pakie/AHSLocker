package com.pakie.ahslocker.sysadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SysAdminServiceImpl implements SysAdminService {

    @Autowired
    private SysAdminRepo sysAdminRepo;

    @Override
    public List<SysAdmin> getAllAdmins() {
        return sysAdminRepo.findAll();
    }

    @Override
    public SysAdmin getAdminByUsername(String username) {
        // Note that the username for admins is stored as email
        return sysAdminRepo.findByEmail(username);
    }

    @Override
    public SysAdmin getAdminByIdNumber(Long id_number) {
        return sysAdminRepo.findByIdNumber(id_number);
    }

    @Override
    public SysAdmin saveAdmin(SysAdmin sysAdmin) {
        sysAdminRepo.save(sysAdmin);
        return sysAdmin;
    }

    @Override
    public SysAdmin getAdminById(Long id) {
        Optional<SysAdmin> optional = sysAdminRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("SysAdmin with id " + id + " not found");
        }
    }

    @Override
    public void deleteAdmin(SysAdmin sysAdmin) {
        sysAdminRepo.delete(sysAdmin);
    }
}
