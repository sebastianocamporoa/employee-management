package com.developer.employeemanagement.controller;

import com.developer.employeemanagement.business.EmployeeBusiness;
import com.developer.employeemanagement.model.EmployeeApiResponse;
import com.developer.employeemanagement.model.EmployeeModel;
import com.developer.employeemanagement.model.EmployeeResponse;
import com.developer.employeemanagement.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
public class EmployeeController {

    private final RestTemplate restTemplate;
    private final EmployeeBusiness employeeBusiness;

    @Autowired
    public EmployeeController(RestTemplate restTemplate, EmployeeBusiness employeeBusiness) {
        this.restTemplate = restTemplate;
        this.employeeBusiness = employeeBusiness;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/employees")
    public ResponseEntity<Object> getEmployees() {
        String apiUrl = "https://dummy.restapiexample.com/api/v1/employees";

        try {
            ResponseEntity<EmployeeResponse> response = restTemplate.getForEntity(apiUrl, EmployeeResponse.class);
            EmployeeResponse employeeResponse = response.getBody();

            if (employeeResponse != null) {
                List<EmployeeModel> employees = employeeResponse.getEmployees();
                for (EmployeeModel employee : employees) {
                    double annualSalary = employeeBusiness.calculateAnnualSalary(employee.getEmployeeSalary());
                    employee.setAnnualSalary(annualSalary);
                }
                return ResponseEntity.ok(employees);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error al obtener la lista de empleados", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/employee/{id}")
    public EmployeeModel getEmployeeById(@PathVariable Long id) {
        String apiUrl = "https://dummy.restapiexample.com/api/v1/employee/" + id;

        ResponseEntity<EmployeeApiResponse> response = restTemplate.getForEntity(apiUrl, EmployeeApiResponse.class);
        EmployeeApiResponse employeeResponse = response.getBody();

        if (employeeResponse != null && employeeResponse.getData() != null) {
            EmployeeModel employee = employeeResponse.getData();
            double annualSalary = employeeBusiness.calculateAnnualSalary(employee.getEmployeeSalary());
            employee.setAnnualSalary(annualSalary);
            return employee;
        } else {
            throw new RuntimeException("Employee not found");
        }
    }
}