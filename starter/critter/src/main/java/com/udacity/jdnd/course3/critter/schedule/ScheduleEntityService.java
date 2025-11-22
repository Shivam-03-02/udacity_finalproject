package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class ScheduleEntityService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    public Schedule createSchedule(List<Long> petIds, List<Long> employeeIds, LocalDate date, Set<com.udacity.jdnd.course3.critter.user.EmployeeSkill> activities){
        Schedule s = new Schedule();
        s.setDate(date);
        Set<Employee> employees = new LinkedHashSet<>();
        for (Long id : employeeIds) employeeRepository.findById(id).ifPresent(employees::add);
        s.setEmployees(employees);
        Set<Pet> pets = new LinkedHashSet<>();
        for (Long id : petIds) petRepository.findById(id).ifPresent(pets::add);
        s.setPets(pets);
        s.setActivities(activities);
        return scheduleRepository.save(s);
    }

    public List<Schedule> findAll(){
        return scheduleRepository.findAllWithPetsAndEmployees();
    }

    public List<Schedule> findByPetId(Long petId){
        return scheduleRepository.findByPetsId(petId);
    }

    public List<Schedule> findByEmployeeId(Long employeeId){
        return scheduleRepository.findByEmployeesId(employeeId);
    }
}
