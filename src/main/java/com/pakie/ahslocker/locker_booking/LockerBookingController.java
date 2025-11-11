package com.pakie.ahslocker.locker_booking;

import com.pakie.ahslocker.locker.Locker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;

@Controller
@RequestMapping("/locker-bookings")
public class LockerBookingController {

    @Autowired
    private LockerBookingService lockerBookingService;

    @GetMapping("/booked-lockers")
    public String getAllBookedLockers(Model model){
        List<LockerBooking> bookedLockers = lockerBookingService.getAllBookedLockers();
        model.addAttribute("bookedLockers", bookedLockers);
        return "/locker/booked_lockers";
    }

    // Get available lockers for a student (current or coming year rules apply)
    @GetMapping("/booked-lockers/available")
    @ResponseBody
    public ResponseEntity<List<Locker>> getAvailableLockersForStudent(@RequestParam("parentIdNumber") Long parentIdNumber,
                                                                      @RequestParam("studentNumber") Long studentNumber,
                                                                      @RequestParam("year") Year year) {
        List<Locker> lockers = lockerBookingService.getAvailableLockersForStudent(parentIdNumber, studentNumber, year);
        return ResponseEntity.ok(lockers);
    }

    // Parent requests a booking (creates a PENDING booking)
    @PostMapping("/request")
    @ResponseBody
    public ResponseEntity<LockerBooking> requestBooking(@RequestParam("parentIdNumber") Long parentIdNumber,
                                                        @RequestParam("studentNumber") Long studentNumber,
                                                        @RequestParam("lockerId") Long lockerId,
                                                        @RequestParam("year") Year year) {
        LockerBooking booking = lockerBookingService.requestBooking(parentIdNumber, studentNumber, lockerId, year);
        return ResponseEntity.ok(booking);
    }

    // Sysadmin approves a booking (marks BOOKED and locker unavailable)
    @PostMapping("/{bookingId}/approve")
    @ResponseBody
    public ResponseEntity<LockerBooking> approveBooking(@PathVariable("bookingId") Long bookingId) {
        LockerBooking booking = lockerBookingService.approveBooking(bookingId);
        return ResponseEntity.ok(booking);
    }

    // Utility for sysadmin: cancel all pending if grade is fully booked for the given year
    @PostMapping("/cancel-pending-if-grade-full")
    @ResponseBody
    public ResponseEntity<String> cancelPendingIfGradeFull(@RequestParam("grade") Integer grade,
                                                           @RequestParam("year") Year year) {
        lockerBookingService.cancelPendingIfGradeFull(grade, year);
        return ResponseEntity.ok("Cancellation check completed for grade " + grade + " and year " + year);
    }

    // Get all bookings for a parent in a specific year
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<LockerBooking>> getBookingsForParent(@RequestParam("parentIdNumber") Long parentIdNumber,
                                                                    @RequestParam("year") Year year) {
        List<LockerBooking> bookings = lockerBookingService.getBookingsForParent(parentIdNumber, year);
        return ResponseEntity.ok(bookings);
    }
}
