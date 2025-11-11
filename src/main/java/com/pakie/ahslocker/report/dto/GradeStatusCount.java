package com.pakie.ahslocker.report.dto;

public class GradeStatusCount {
    private int grade;
    private long pending;
    private long cancelled;

    public GradeStatusCount() {}

    public GradeStatusCount(int grade, long pending, long cancelled) {
        this.grade = grade;
        this.pending = pending;
        this.cancelled = cancelled;
    }

    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }

    public long getPending() { return pending; }
    public void setPending(long pending) { this.pending = pending; }

    public long getCancelled() { return cancelled; }
    public void setCancelled(long cancelled) { this.cancelled = cancelled; }
}
