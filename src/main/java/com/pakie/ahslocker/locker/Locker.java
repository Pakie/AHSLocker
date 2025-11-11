package com.pakie.ahslocker.locker;

import com.pakie.ahslocker.locker_booking.LockerBooking;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "lockers")
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;
    private String lockerGrade;
    private Boolean isAvailable;
    @OneToMany(mappedBy = "locker")
    private List<LockerBooking> lockerBookings;

    public Locker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLockerGrade() {
        return lockerGrade;
    }

    public void setLockerGrade(String lockerGrade) {
        this.lockerGrade = lockerGrade;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean available) {
        isAvailable = available;
    }

    public List<LockerBooking> getLockerBookings() {
        return lockerBookings;
    }

    public void setLockerBookings(List<LockerBooking> lockerBookings) {
        this.lockerBookings = lockerBookings;
    }
}
