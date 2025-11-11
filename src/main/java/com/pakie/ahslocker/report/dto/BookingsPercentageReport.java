package com.pakie.ahslocker.report.dto;

import java.util.List;

public class BookingsPercentageReport {
    private int year;
    private long totalBooked;
    private List<GradePercentageItem> items;

    public BookingsPercentageReport() {}

    public BookingsPercentageReport(int year, long totalBooked, List<GradePercentageItem> items) {
        this.year = year;
        this.totalBooked = totalBooked;
        this.items = items;
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public long getTotalBooked() { return totalBooked; }
    public void setTotalBooked(long totalBooked) { this.totalBooked = totalBooked; }

    public List<GradePercentageItem> getItems() { return items; }
    public void setItems(List<GradePercentageItem> items) { this.items = items; }
}
