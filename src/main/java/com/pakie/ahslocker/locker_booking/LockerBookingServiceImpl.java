package com.pakie.ahslocker.locker_booking;

import com.pakie.ahslocker.locker.Locker;
import com.pakie.ahslocker.locker.LockerRepo;
import com.pakie.ahslocker.parent.Parent;
import com.pakie.ahslocker.parent.ParentRepo;
import com.pakie.ahslocker.student.Student;
import com.pakie.ahslocker.student.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LockerBookingServiceImpl implements LockerBookingService {

    @Autowired
    private LockerBookingRepo lockerBookingRepo;
    @Autowired
    private LockerRepo lockerRepo;
    @Autowired
    private ParentRepo parentRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Override
    public List<LockerBooking> getAllBookedLockers() {
        return lockerBookingRepo.findAll();
    }

    @Override
    public List<Locker> getAvailableLockersForStudent(Long parentIdNumber, Long studentNumber, Year year) {
        Parent parent = requireParent(parentIdNumber);
        Student student = requireStudent(studentNumber);
        assertStudentBelongsToParent(student, parent);
        int targetGrade = resolveTargetGrade(student.getGrade(), year);
        String targetGradeLabel = String.valueOf(targetGrade);
        // The in-memory filter is used to avoid changing repository signatures
        return lockerRepo.findAll()
                .stream()
                .filter(l -> Boolean.TRUE.equals(l.getIsAvailable()))
                .filter(l -> targetGradeLabel.equals(l.getLockerGrade()))
                .sorted(Comparator.comparing(Locker::getLabel))
                .collect(Collectors.toList());
    }

    @Override
    public LockerBooking requestBooking(Long parentIdNumber, Long studentNumber, Long lockerId, Year year) {
        Parent parent = requireParent(parentIdNumber);
        Student student = requireStudent(studentNumber);
        assertStudentBelongsToParent(student, parent);
        Locker locker = requireLocker(lockerId);
        int targetGrade = resolveTargetGrade(student.getGrade(), year);
        String targetGradeLabel = String.valueOf(targetGrade);

        if (!targetGradeLabel.equals(locker.getLockerGrade())) {
            throw new RuntimeException("Locker is not for the student's allowed grade for the selected year");
        }

        // Only one booking per student per year
        boolean alreadyRequested = lockerBookingRepo.findByYear(year)
                .stream()
                .anyMatch(b -> b.getStudent().getStudentNumber().equals(studentNumber)); //TODO: check status as well to just in case the student has a cancelled booking.
        if (alreadyRequested) {
            throw new RuntimeException("Student already has a booking for year " + year);
        }

        // Create a PENDING booking regardless of locker availability; availability is enforced on approval
        //TODO: consider checking if the grade is already full here and prevent booking if so.
        LockerBooking booking = new LockerBooking();
        booking.setLocker( locker );
        booking.setYear( year );
        booking.setParent( parent );
        booking.setStudent( student );
        booking.setStatus( BookingStatus.PENDING );
        lockerBookingRepo.save(booking);
        return booking;
    }

    @Override
    @Transactional
    public LockerBooking approveBooking(Long bookingId) {
        Optional<LockerBooking> optional = lockerBookingRepo.findById(bookingId);
        LockerBooking booking;
        if (optional.isPresent()) {
            booking = optional.get();
        } else {
            throw new RuntimeException("Booking with id " + bookingId + " not found");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Only PENDING bookings can be approved");
        }

        Locker locker = requireLocker(booking.getLocker().getId());
        if (!Boolean.TRUE.equals(locker.getIsAvailable())) {
            throw new RuntimeException("Locker is no longer available");
        }

        // Approve booking and mark locker unavailable
        booking.setStatus(BookingStatus.BOOKED);
        locker.setIsAvailable(false);
        lockerRepo.save(locker);
        lockerBookingRepo.save(booking);

        // After approval, if no lockers remain for the grade and year, cancel remaining PENDING for that grade/year
        Integer grade = parseLockerGrade(locker.getLockerGrade());
        cancelPendingIfGradeFull(grade, booking.getYear());
        return booking;
    }

    @Override
    @Transactional
    public void cancelPendingIfGradeFull(Integer grade, Year year) {
        String gradeLabel = String.valueOf(grade);
        boolean anyAvailable = lockerRepo.findAll()
                .stream()
                .anyMatch(l -> gradeLabel.equals(l.getLockerGrade()) && Boolean.TRUE.equals(l.getIsAvailable()));
        if (!anyAvailable) {
            // No lockers available for this grade — cancel all pending bookings for this grade and year
            List<LockerBooking> pendings = lockerBookingRepo.findByYear(year)
                    .stream()
                    .filter(b -> b.getStatus() == BookingStatus.PENDING)
                    .filter(b -> gradeLabel.equals(b.getLocker().getLockerGrade()))
                    .collect(Collectors.toList());
            for (LockerBooking b : pendings) {
                b.setStatus(BookingStatus.CANCELLED);
                lockerBookingRepo.save(b);
            }
        }
    }

    @Override
    public List<LockerBooking> getBookingsForParent(Long parentIdNumber, Year year) {
        Parent parent = requireParent(parentIdNumber);
        return lockerBookingRepo.findByYear(year)
                .stream()
                .filter(b -> b.getParent().getParentIdNumber().equals(parent.getParentIdNumber()))
                .sorted(Comparator.comparing(LockerBooking::getCreatedAt))
                .collect(Collectors.toList());
    }

    private Parent requireParent(Long parentIdNumber) {
        Parent parent = parentRepo.findByParentIdNumber(parentIdNumber);
        if (parent == null) {
            throw new RuntimeException("Parent with id number " + parentIdNumber + " not found");
        }
        return parent;
    }

    private Student requireStudent(Long studentNumber) {
        Student student = studentRepo.findByStudentNumber(studentNumber);
        if (student == null) {
            throw new RuntimeException("Student with number " + studentNumber + " not found");
        }
        return student;
    }

    private Locker requireLocker(Long lockerId) {
        Optional<Locker> optional = lockerRepo.findById(lockerId);
        Locker locker = null;
        if (optional.isPresent()) {
            locker = optional.get();
        } else {
            throw new RuntimeException("Locker with id " + lockerId + " not found");
        }
        return locker;
    }

    private void assertStudentBelongsToParent(Student student, Parent parent) {
        if (student.getParent() == null || !parent.getParentIdNumber().equals(student.getParent().getParentIdNumber())) {
            throw new RuntimeException("Student is not linked to the specified parent");
        }
    }

    private int resolveTargetGrade(int currentStudentGrade, Year year) {
        int currentYear = Year.now().getValue();
        if (year == null) {
            throw new RuntimeException("Year must be provided");
        }
        if (year.getValue() == currentYear) {
            return currentStudentGrade;
        } else if (year.getValue() == currentYear + 1) {
            return currentStudentGrade + 1;
        } else {
            throw new RuntimeException("Bookings are only allowed for the current or coming year");
        }
    }

    private Integer parseLockerGrade(String gradeLabel) {
        try {
            return Integer.parseInt(gradeLabel);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid locker grade label: " + gradeLabel);
        }
    }
}
