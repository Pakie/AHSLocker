package com.pakie.ahslocker.report;

import com.pakie.ahslocker.report.dto.AvailabilityReport;
import com.pakie.ahslocker.report.dto.BookingsPercentageReport;
import com.pakie.ahslocker.report.dto.GradeStatusCount;

import java.util.List;

public interface ReportService {
    AvailabilityReport getAvailability(int year);
    BookingsPercentageReport getBookingsPercentage(int year);
    //List<GradeStatusCount> getPendingCancelledPerGrade(int year);
    List<GradeStatusCount> getPendingPerGrade(int year);
    List<GradeStatusCount> getCancelledPerGrade(int year);
}
