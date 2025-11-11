package com.pakie.ahslocker.locker_booking;

import com.pakie.ahslocker.locker.Locker;
import com.pakie.ahslocker.parent.Parent;
import com.pakie.ahslocker.student.Student;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.Year;

@Entity
@Table(name = "lockerBookings", uniqueConstraints = @UniqueConstraint(columnNames = {"locker_id", "year"}))
public class LockerBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Locker locker;
    private Year year;
    @ManyToOne(optional = false)
    private Parent parent;
    @ManyToOne(optional = false)
    private Student student;
    @Enumerated(EnumType.STRING)
    private BookingStatus status; //BOOKED, PENDING, CANCELLED from the ENUM
    private LocalDateTime createdAt = LocalDateTime.now();

    public LockerBooking() {
    }

    public LockerBooking(Locker locker, Year year, Parent parent, Student student, BookingStatus status, LocalDateTime createdAt) {
        this.locker = locker;
        this.year = year;
        this.parent = parent;
        this.student = student;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LockerBooking{" +
                "id=" + id +
                ", locker=" + locker +
                ", year=" + year +
                ", parent=" + parent +
                ", student=" + student +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
