package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO){
        Employee e = new Employee();
        e.setName(employeeDTO.getName());
        e.setSkills(employeeDTO.getSkills());
        e.setDaysAvailable(employeeDTO.getDaysAvailable());
        Employee saved = employeeRepository.save(e);
        EmployeeDTO out = new EmployeeDTO();
        out.setId(saved.getId());
        out.setName(saved.getName());
        out.setSkills(saved.getSkills());
        out.setDaysAvailable(saved.getDaysAvailable());
        return out;
    }

    public EmployeeDTO getEmployee(long employeeId){
        Optional<Employee> o = employeeRepository.findById(employeeId);
        if (o.isEmpty()) return null;
        Employee e = o.get();
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setSkills(e.getSkills());
        dto.setDaysAvailable(e.getDaysAvailable());
        return dto;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId){
        Optional<Employee> o = employeeRepository.findById(employeeId);
        if (o.isPresent()){
            Employee e = o.get();
            e.setDaysAvailable(daysAvailable);
            employeeRepository.save(e);
        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO){
        List<Employee> all = employeeRepository.findAll();
        List<EmployeeDTO> res = new ArrayList<>();
        DayOfWeek reqDay = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        for (Employee e : all) {
            if (e.getDaysAvailable() != null && e.getDaysAvailable().contains(reqDay)
                    && e.getSkills() != null && e.getSkills().containsAll(skills)){
                EmployeeDTO dto = new EmployeeDTO();
                dto.setId(e.getId());
                dto.setName(e.getName());
                dto.setSkills(e.getSkills());
                dto.setDaysAvailable(e.getDaysAvailable());
                res.add(dto);
            }
        }
        return res;
    }
}
