package com.pakie.ahslocker.locker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LockerServiceImpl implements LockerService {

    @Autowired
    private LockerRepo lockerRepo;

    @Override
    public List<Locker> findAllLockers() {
        return lockerRepo.findAll();
    }

    @Override
    public List<Locker> getLockerByGradeAndIsAvailable(Locker locker, Boolean isAvailable) {
        return lockerRepo.findByLockerGradeAndIsAvailable(locker, isAvailable);
    }

    @Override
    public Locker findLockerById(Long id) {
        Optional<Locker> optional = lockerRepo.findById(id);
        Locker locker = null;
        if (optional.isPresent()) {
            locker = optional.get();
        } else {
            throw new RuntimeException("Locker with id " + id + " not found");
        }
        return locker;
    }

    @Override
    public Locker saveLocker(Locker locker) {
        // Enforce per-grade locker limits: Grade 12 → max 5, Grade 8 → max 10
        enforceLockerGradeLimits(locker);
        lockerRepo.save(locker);
        return locker;
    }

    @Override
    public void deleteLockerById(Long id) {
        lockerRepo.deleteById(id);
    }

    private void enforceLockerGradeLimits(Locker locker) {
        String gradeStr = locker.getLockerGrade();
        int grade = extractNumericGrade(gradeStr);
        if (grade != 12 && grade != 8) {
            return; // No limits for other grades
        }

        // Count existing lockers for the target grade. Exclude self if updating.
        Long selfId = locker.getId();
        AtomicInteger count = new AtomicInteger(0);
        lockerRepo.findAll().forEach(l -> {
            if (l.getId() != null && selfId != null && l.getId().equals(selfId)) {
                return; // exclude current record when updating
            }
            if (extractNumericGrade(l.getLockerGrade()) == grade) {
                count.incrementAndGet();
            }
        });

        int limit = (grade == 12) ? 5 : 10;
        if (count.get() >= limit) {
            throw new IllegalStateException("Maximum lockers for Grade " + grade + " reached (limit: " + limit + ").");
        }
    }

    private int extractNumericGrade(String gradeStr) {
        if (gradeStr == null) return -1;
        StringBuilder digits = new StringBuilder();
        for (char c : gradeStr.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.append(c);
            } else if (digits.length() > 0) {
                // stop at first non-digit after digits start to avoid capturing things like room numbers (e.g., "Grade 12A")
                break;
            }
        }
        try {
            return digits.length() == 0 ? -1 : Integer.parseInt(digits.toString());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
