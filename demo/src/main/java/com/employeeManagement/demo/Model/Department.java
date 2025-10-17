package com.employeeManagement.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String manager;
    @Column(nullable = false)
    private String action;

    // Constructors
    public Department() {}
    public Department(String name) {
        this.name = name;
    }
}
