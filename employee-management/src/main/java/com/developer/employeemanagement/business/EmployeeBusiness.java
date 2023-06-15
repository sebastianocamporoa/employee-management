package com.developer.employeemanagement.business;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class EmployeeBusiness {

    public double calculateAnnualSalary(double monthlySalary) {
        return monthlySalary * 12;
    }
}