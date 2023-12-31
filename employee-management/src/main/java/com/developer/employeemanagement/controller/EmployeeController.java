package com.developer.employeemanagement.controller;

import com.developer.employeemanagement.business.EmployeeBusiness;
import com.developer.employeemanagement.model.EmployeeApiResponse;
import com.developer.employeemanagement.model.EmployeeModel;
import com.developer.employeemanagement.model.EmployeeResponse;
import com.developer.employeemanagement.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class EmployeeController {

    private final RestTemplate restTemplate;
    private final EmployeeBusiness employeeBusiness;

    @Autowired
    public EmployeeController(RestTemplate restTemplate, EmployeeBusiness employeeBusiness) {
        this.restTemplate = restTemplate;
        this.employeeBusiness = employeeBusiness;
    }

    //@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
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
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        String apiUrl = "https://dummy.restapiexample.com/api/v1/employee/" + id;

        try {
            ResponseEntity<EmployeeApiResponse> response = restTemplate.getForEntity(apiUrl, EmployeeApiResponse.class);
            EmployeeApiResponse employeeResponse = response.getBody();

            if (employeeResponse != null && employeeResponse.getData() != null) {
                EmployeeModel employee = employeeResponse.getData();
                double annualSalary = employeeBusiness.calculateAnnualSalary(employee.getEmployeeSalary());
                employee.setAnnualSalary(annualSalary);
                return ResponseEntity.ok(employee);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Employee not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving employee", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}