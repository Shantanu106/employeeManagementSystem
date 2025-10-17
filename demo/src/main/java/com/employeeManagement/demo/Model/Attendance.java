package com.employeeManagement.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId; // which employee
    private LocalDate date;
    private boolean present;

    // Constructors, getters, setters
   // public Attendance(){}

    public Attendance(Long employeeId, LocalDate now, boolean present) {
    }
}
