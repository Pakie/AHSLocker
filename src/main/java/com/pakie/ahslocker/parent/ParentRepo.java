package com.pakie.ahslocker.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepo extends JpaRepository<Parent, Long> {
    Parent findByParentEmailAddress(String parentEmailAddress);
    Parent findByParentIdNumber(Long parentIdNumber);
}
