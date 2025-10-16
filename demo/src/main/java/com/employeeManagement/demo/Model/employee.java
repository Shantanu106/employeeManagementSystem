package com.employeeManagement.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class employee {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Department is required")
    private String department;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;

   // @Min(value = 1000, message = "Salary must be at least 1000")
    private double salary;
    public employee(){};
}
