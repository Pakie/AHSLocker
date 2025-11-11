package com.pakie.ahslocker.report;

import com.pakie.ahslocker.locker.Locker;
import com.pakie.ahslocker.locker.LockerRepo;
import com.pakie.ahslocker.locker_booking.BookingStatus;
import com.pakie.ahslocker.locker_booking.LockerBookingRepo;
import com.pakie.ahslocker.report.dto.AvailabilityReport;
import com.pakie.ahslocker.report.dto.BookingsPercentageReport;
import com.pakie.ahslocker.report.dto.GradePercentageItem;
import com.pakie.ahslocker.report.dto.GradeStatusCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private LockerRepo lockerRepo;
    @Autowired
    private LockerBookingRepo lockerBookingRepo;

    @Override
    public AvailabilityReport getAvailability(int year) {
        long totalLockers = lockerRepo.count();
        Year y = Year.of(year);
        long booked = lockerBookingRepo.countByYearAndStatus(y, BookingStatus.BOOKED);
        long available = Math.max(0, totalLockers - booked);
        return new AvailabilityReport(year, totalLockers, booked, available);
    }

    @Override
    public BookingsPercentageReport getBookingsPercentage(int year) {
        Year y = Year.of(year);
        long totalBooked = lockerBookingRepo.countByYearAndStatus(y, BookingStatus.BOOKED);
        List<Integer> grades = getAllNumericGrades();
        List<GradePercentageItem> items = new ArrayList<>();
        for (Integer g : grades) {
            long count = lockerBookingRepo.countByYearAndStatusAndStudent_Grade(y, BookingStatus.BOOKED, g);
            double pct = totalBooked == 0 ? 0.0 : (count * 100.0) / totalBooked;
            items.add(new GradePercentageItem(g, count, round1(pct)));
        }
        // Sort grades ascending for stable charts
        items = items.stream()
                .sorted(Comparator.comparingInt(GradePercentageItem::getGrade))
                .collect(Collectors.toList());
        return new BookingsPercentageReport(year, totalBooked, items);
    }

    /*@Override
    public List<GradeStatusCount> getPendingCancelledPerGrade(int year) {
        Year y = Year.of(year);
        List<Integer> grades = getAllNumericGrades();
        List<GradeStatusCount> list = new ArrayList<>();
        for (Integer g : grades) {
            long pending = lockerBookingRepo.countByYearAndStatusAndStudent_Grade(y, BookingStatus.PENDING, g);
            long cancelled = lockerBookingRepo.countByYearAndStatusAndStudent_Grade(y, BookingStatus.CANCELLED, g);
            list.add(new GradeStatusCount(g, pending, cancelled));
        }
        return list.stream()
                .sorted(Comparator.comparingInt(GradeStatusCount::getGrade))
                .collect(Collectors.toList());
    }*/

    public List<GradeStatusCount> getPendingPerGrade(int year) {
        Year y = Year.of(year);
        return getPerGradeForStatus(y, BookingStatus.PENDING, true);
    }

    public List<GradeStatusCount> getCancelledPerGrade(int year) {
        Year y = Year.of(year);
        return getPerGradeForStatus(y, BookingStatus.CANCELLED, false);
    }

    private List<GradeStatusCount> getPerGradeForStatus(Year y, BookingStatus status, boolean fillPending) {
        List<Integer> grades = getAllNumericGrades();
        List<GradeStatusCount> list = new ArrayList<>();
        for (Integer g : grades) {
            long count = lockerBookingRepo.countByYearAndStatusAndStudent_Grade(y, status, g);
            long pending = fillPending ? count : 0L;
            long cancelled = fillPending ? 0L : count;
            list.add(new GradeStatusCount(g, pending, cancelled));
        }
        return list.stream()
                .sorted(Comparator.comparingInt(GradeStatusCount::getGrade))
                .collect(Collectors.toList());
    }

    private List<Integer> getAllNumericGrades() {
        List<Locker> lockers = lockerRepo.findAll();
        Set<Integer> grades = lockers.stream()
                .map(Locker::getLockerGrade)
                .filter(s -> s != null && !s.isBlank())
                .map(s -> {
                    try { return Integer.parseInt(s.trim()); } catch (NumberFormatException e) { return null; }
                })
                .filter(g -> g != null)
                .collect(Collectors.toSet());
        return grades.stream().sorted().collect(Collectors.toList());
    }

    private double round1(double v) {
        return Math.round(v * 10.0) / 10.0;
    }
}
