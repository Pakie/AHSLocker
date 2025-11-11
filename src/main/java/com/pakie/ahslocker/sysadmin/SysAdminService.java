package com.pakie.ahslocker.sysadmin;

import java.util.List;

public interface SysAdminService {
    List<SysAdmin> getAllAdmins();
    SysAdmin getAdminByUsername(String username);
    SysAdmin getAdminByIdNumber(Long id_number);
    SysAdmin saveAdmin(SysAdmin sysAdmin);
    SysAdmin getAdminById(Long id);
    void deleteAdmin(SysAdmin sysAdmin);
}
