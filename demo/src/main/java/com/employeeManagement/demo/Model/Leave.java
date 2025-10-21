package com.employeeManagement.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "leaves")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private String employeeName;
    private String department;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status = "Pending"; // default status
    public  Leave(){}
}
