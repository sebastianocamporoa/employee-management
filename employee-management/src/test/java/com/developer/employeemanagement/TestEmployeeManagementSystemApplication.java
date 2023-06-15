package com.developer.employeemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
class TestEmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(EmployeeManagementSystemApplication::main).with(TestEmployeeManagementSystemApplication.class).run(args);
	}

}
