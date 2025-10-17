package com.employeeManagement.demo.Service;

import com.employeeManagement.demo.Model.Attendance;
import com.employeeManagement.demo.Repository.AttendanceRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepo attendanceRepo;

    public AttendanceService(AttendanceRepo attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepo.findAll();
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepo.findByDate(date);
    }

    public void markAttendance(Long employeeId, boolean present) {
        Attendance attendance = new Attendance(employeeId, LocalDate.now(), present);
        attendanceRepo.save(attendance);
    }
    public Attendance getAttendanceByEmployeeAndDate(Long employeeId, LocalDate date) {
        return attendanceRepo.findByEmployeeIdAndDate(employeeId, date).orElse(null);
    }

}