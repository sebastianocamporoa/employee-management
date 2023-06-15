package com.developer.employeemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeApiResponse {
    private String status;
    private EmployeeModel data;
    private String message;
}