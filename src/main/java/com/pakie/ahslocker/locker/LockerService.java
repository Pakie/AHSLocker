package com.pakie.ahslocker.locker;

import java.util.List;

public interface LockerService {
    List<Locker> findAllLockers();
    List<Locker> getLockerByGradeAndIsAvailable(Locker locker, Boolean isAvailable);
    Locker findLockerById(Long id);
    Locker saveLocker(Locker locker);
    void  deleteLockerById(Long id);
}
