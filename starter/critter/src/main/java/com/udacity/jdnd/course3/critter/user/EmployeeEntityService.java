package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeEntityService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee e){
        return employeeRepository.save(e);
    }

    public Employee findById(Long id){
        Optional<Employee> o = employeeRepository.findById(id);
        return o.orElse(null);
    }

    public void setAvailability(Long id, Set<DayOfWeek> days){
        Optional<Employee> o = employeeRepository.findById(id);
        if (o.isPresent()){
            Employee e = o.get();
            e.setDaysAvailable(days);
            employeeRepository.save(e);
        }
    }

    public List<Employee> findForService(LocalDate date, Set<EmployeeSkill> skills){
        List<Employee> all = employeeRepository.findAll();
        List<Employee> res = new ArrayList<>();
        for (Employee e: all){
            if (e.getDaysAvailable() != null && e.getDaysAvailable().contains(date.getDayOfWeek())
                    && e.getSkills() != null && e.getSkills().containsAll(skills)){
                res.add(e);
            }
        }
        return res;
    }
}
