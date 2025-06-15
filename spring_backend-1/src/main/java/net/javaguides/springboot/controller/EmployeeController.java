package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	// create Employee REST API
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//Get Employee by Id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee>getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Employee does not exist with id : "+id));
		return ResponseEntity.ok(employee);
	}
	
	//Update Employee Rest API
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeWithUpdation) {
		Employee employeeToBeUpdate = employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id : "+id));
		
			employeeToBeUpdate.setFirstName(employeeWithUpdation.getFirstName());
			employeeToBeUpdate.setLastName(employeeWithUpdation.getLastName());
			employeeToBeUpdate.setEmailId(employeeWithUpdation.getEmailId());
			Employee updatedEmployee = employeeRepository.save(employeeToBeUpdate);
			return ResponseEntity.ok(updatedEmployee);
	}
	
	//delete employee rest API
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String,Boolean>>deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id : "+id));
		
		employeeRepository.delete(employee);
		
		Map<String,Boolean> response = new HashMap<String, Boolean>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
		
	}
	

}
