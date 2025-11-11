package com.pakie.ahslocker.sysadmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAdminRepo extends JpaRepository<SysAdmin, Long>{
    SysAdmin findByEmail(String email);
    SysAdmin findByIdNumber(Long idNumber);
}
