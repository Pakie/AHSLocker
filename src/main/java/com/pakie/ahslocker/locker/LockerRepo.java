package com.pakie.ahslocker.locker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LockerRepo extends JpaRepository<Locker, Long> {
    List<Locker> findByLockerGradeAndIsAvailable(Locker locker, Boolean isAvailable);

    // Count lockers by grade (exact match)
    long countByLockerGrade(String lockerGrade);

    // Count lockers where the grade string contains the given token case-insensitively (e.g., "12" matches "Grade 12")
    long countByLockerGradeContainingIgnoreCase(String gradeToken);
}
