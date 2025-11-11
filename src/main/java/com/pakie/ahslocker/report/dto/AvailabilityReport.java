package com.pakie.ahslocker.report.dto;

public class AvailabilityReport {
    private int year;
    private long totalLockers;
    private long bookedLockers;
    private long availableLockers;

    public AvailabilityReport() {}

    public AvailabilityReport(int year, long totalLockers, long bookedLockers, long availableLockers) {
        this.year = year;
        this.totalLockers = totalLockers;
        this.bookedLockers = bookedLockers;
        this.availableLockers = availableLockers;
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public long getTotalLockers() { return totalLockers; }
    public void setTotalLockers(long totalLockers) { this.totalLockers = totalLockers; }

    public long getBookedLockers() { return bookedLockers; }
    public void setBookedLockers(long bookedLockers) { this.bookedLockers = bookedLockers; }

    public long getAvailableLockers() { return availableLockers; }
    public void setAvailableLockers(long availableLockers) { this.availableLockers = availableLockers; }
}
