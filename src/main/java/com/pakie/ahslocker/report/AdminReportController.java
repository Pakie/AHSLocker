package com.pakie.ahslocker.report;

import com.pakie.ahslocker.report.dto.AvailabilityReport;
import com.pakie.ahslocker.report.dto.BookingsPercentageReport;
import com.pakie.ahslocker.report.dto.GradeStatusCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    @Autowired
    private ReportService reportService;

    // Availability for a specific year
    @GetMapping("/availability")
    public AvailabilityReport availability(@RequestParam("year") int year) {
        return reportService.getAvailability(year);
    }

    // Convenience endpoint: availability for current and next year
    @GetMapping("/availability/current-next")
    public List<AvailabilityReport> availabilityCurrentAndNext() {
        int current = Year.now().getValue();
        int next = current + 1;
        return Arrays.asList(
                reportService.getAvailability(current),
                reportService.getAvailability(next)
        );
    }

    // Percentage of bookings per grade for a given year
    @GetMapping("/bookings-percentage")
    public BookingsPercentageReport bookingsPercentage(@RequestParam("year") int year) {
        return reportService.getBookingsPercentage(year);
    }

    @GetMapping("/pending")
    public List<GradeStatusCount> pending(@RequestParam("year") int year) {
        return reportService.getPendingPerGrade(year);
    }

    @GetMapping("/cancelled")
    public List<GradeStatusCount> cancelled(@RequestParam("year") int year) {
        return reportService.getCancelledPerGrade(year);
    }

    // Pending and cancelled counts per grade for a given year
    /*@GetMapping("/pending-cancelled")
    public List<GradeStatusCount> pendingCancelled(@RequestParam("year") int year) {
        return reportService.getPendingCancelledPerGrade(year);
    }*/
}
