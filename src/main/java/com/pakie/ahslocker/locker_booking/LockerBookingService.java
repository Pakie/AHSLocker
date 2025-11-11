package com.pakie.ahslocker.locker_booking;

import com.pakie.ahslocker.locker.Locker;

import java.time.Year;
import java.util.List;

public interface LockerBookingService {
    List<LockerBooking> getAllBookedLockers();
    List<Locker> getAvailableLockersForStudent(Long parentIdNumber, Long studentNumber, Year year);
    LockerBooking requestBooking(Long parentIdNumber, Long studentNumber, Long lockerId, Year year);
    LockerBooking approveBooking(Long bookingId);
    void cancelPendingIfGradeFull(Integer grade, Year year);
    List<LockerBooking> getBookingsForParent(Long parentIdNumber, Year year);
}