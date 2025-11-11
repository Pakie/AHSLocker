package com.pakie.ahslocker.locker_booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface LockerBookingRepo extends JpaRepository<LockerBooking, Long> {
    Optional<LockerBooking> findByLockerIdAndYear(Long lockerId, Year year);
    List<LockerBooking> findByYear(Year year);
    long countByYearAndStudent_Grade(Year year, Integer grade);

    long countByYearAndStatus(Year year, BookingStatus status);
    long countByYearAndStatusAndStudent_Grade(Year year, BookingStatus status, Integer grade);
}
