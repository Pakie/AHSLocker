package com.pakie.ahslocker.report.dto;

public class GradePercentageItem {
    private int grade;
    private long bookings;
    private double percentage;

    public GradePercentageItem() {}

    public GradePercentageItem(int grade, long bookings, double percentage) {
        this.grade = grade;
        this.bookings = bookings;
        this.percentage = percentage;
    }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    public long getBookings() { return bookings; }
    public void setBookings(long bookings) { this.bookings = bookings; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
}
