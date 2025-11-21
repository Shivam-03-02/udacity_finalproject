package com.udacity.jdnd.course3.critter.user;

 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.*;
import com.udacity.jdnd.course3.critter.util.DTOMapper;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private CustomerEntityService customerEntityService;

    @Autowired
    private EmployeeEntityService employeeEntityService;

    @Autowired
    private DTOMapper mapper;

    

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer c = new Customer();
        c.setName(customerDTO.getName());
        c.setPhoneNumber(customerDTO.getPhoneNumber());
        c.setNotes(customerDTO.getNotes());
        Customer saved = customerEntityService.save(c);
        return mapper.toCustomerDTO(saved);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerEntityService.findAll();
        List<CustomerDTO> result = new ArrayList<>();
        for (Customer c : customers) result.add(mapper.toCustomerDTO(c));
        return result;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer c = customerEntityService.findOwnerByPetId(petId);
        if (c == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");
        return mapper.toCustomerDTO(c);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee e = new Employee();
        e.setName(employeeDTO.getName());
        e.setSkills(employeeDTO.getSkills());
        e.setDaysAvailable(employeeDTO.getDaysAvailable());
        Employee saved = employeeEntityService.save(e);
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setSkills(saved.getSkills());
        dto.setDaysAvailable(saved.getDaysAvailable());
        return dto;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee e = employeeEntityService.findById(employeeId);
        if (e == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        return mapper.toEmployeeDTO(e);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeEntityService.setAvailability(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> matches = employeeEntityService.findForService(employeeDTO.getDate(), employeeDTO.getSkills());
        List<EmployeeDTO> result = new ArrayList<>();
        for (Employee e : matches) result.add(mapper.toEmployeeDTO(e));
        return result;
    }

}
