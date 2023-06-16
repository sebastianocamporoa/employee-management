package com.developer.employeemanagement.controller;

import com.developer.employeemanagement.business.EmployeeBusiness;
import com.developer.employeemanagement.controller.EmployeeController;
import com.developer.employeemanagement.model.EmployeeApiResponse;
import com.developer.employeemanagement.model.EmployeeModel;
import com.developer.employeemanagement.model.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static graphql.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EmployeeBusiness employeeBusiness;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController(restTemplate, employeeBusiness);
    }

    @Test
    public void testGetEmployees() {
        // Mock data
        EmployeeModel employee1 = new EmployeeModel(1L, "John Doe", 5000, 45, "", 0);
        EmployeeModel employee2 = new EmployeeModel(2L, "Jane Smith", 6000, 55, "", 0);
        List<EmployeeModel> expectedEmployees = Arrays.asList(employee1, employee2);

        // Mock RestTemplate's getForEntity method
        when(restTemplate.getForEntity(anyString(), eq(EmployeeResponse.class)))
                .thenReturn(new ResponseEntity<>(new EmployeeResponse(expectedEmployees), HttpStatus.OK));

        // Mock employee business
        when(employeeBusiness.calculateAnnualSalary(5000.0)).thenReturn(60000.0);
        when(employeeBusiness.calculateAnnualSalary(6000.0)).thenReturn(72000.0);

        // Call the controller method
        ResponseEntity<Object> responseEntity = employeeController.getEmployees();

        // Verify RestTemplate's getForEntity method is called with the correct URL and class
        verify(restTemplate, times(1)).getForEntity(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(EmployeeResponse.class));

        // Verify employee business's calculateAnnualSalary method is called for each employee
        verify(employeeBusiness, times(1)).calculateAnnualSalary(5000.0);
        verify(employeeBusiness, times(1)).calculateAnnualSalary(6000.0);

        // Assert the response entity has the correct status code and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof List);

        List<?> actualEmployees = (List<?>) responseEntity.getBody();

        // Assert the returned employees have the correct values
        assertEquals(expectedEmployees.size(), actualEmployees.size());

        for (int i = 0; i < expectedEmployees.size(); i++) {
            EmployeeModel expectedEmployee = expectedEmployees.get(i);
            EmployeeModel actualEmployee = (EmployeeModel) actualEmployees.get(i);

            assertEquals(expectedEmployee.getId(), actualEmployee.getId());
            assertEquals(expectedEmployee.getEmployeeName(), actualEmployee.getEmployeeName());
            assertEquals(expectedEmployee.getEmployeeSalary(), actualEmployee.getEmployeeSalary());
            assertEquals(expectedEmployee.getEmployeeAge(), actualEmployee.getEmployeeAge());
            assertEquals(expectedEmployee.getProfileImage(), actualEmployee.getProfileImage());
        }
    }

    @Test
    public void testGetEmployeeById() {
        // Mock data
        EmployeeModel expectedEmployee = new EmployeeModel(1L, "John Doe", 5000, 45, "", 0);

        // Mock RestTemplate's getForEntity method
        when(restTemplate.getForEntity(eq("https://dummy.restapiexample.com/api/v1/employee/1"), eq(EmployeeApiResponse.class)))
                .thenReturn(new ResponseEntity<>(new EmployeeApiResponse("success", expectedEmployee, ""), HttpStatus.OK));

        // Mock employee business
        when(employeeBusiness.calculateAnnualSalary(5000.0)).thenReturn(60000.0);

        // Call the controller method
        ResponseEntity<?> responseEntity = employeeController.getEmployeeById(1L);
        EmployeeModel actualEmployee = (responseEntity.getBody() instanceof EmployeeModel) ? (EmployeeModel) responseEntity.getBody() : null;

        // Verify RestTemplate's getForEntity method is called with the correct URL and class
        verify(restTemplate, times(1)).getForEntity(eq("https://dummy.restapiexample.com/api/v1/employee/1"), eq(EmployeeApiResponse.class));

        // Verify employee business's calculateAnnualSalary method is called for the employee
        verify(employeeBusiness, times(1)).calculateAnnualSalary(5000.0);

        // Assert the returned employee has the correct values
        assertEquals(expectedEmployee.getId(), actualEmployee.getId());
        assertEquals(expectedEmployee.getEmployeeName(), actualEmployee.getEmployeeName());
        assertEquals(60000.0, actualEmployee.getAnnualSalary());
    }
}