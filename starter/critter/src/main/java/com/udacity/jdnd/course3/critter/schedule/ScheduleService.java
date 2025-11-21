package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;


    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO){
        Schedule s = new Schedule();
        s.setDate(scheduleDTO.getDate());
        Set<Employee> employees = new LinkedHashSet<>();
        for (Long id : scheduleDTO.getEmployeeIds()) {
            employeeRepository.findById(id).ifPresent(employees::add);
        }
        s.setEmployees(employees);
        Set<Pet> pets = new LinkedHashSet<>();
        for (Long id : scheduleDTO.getPetIds()) {
            petRepository.findById(id).ifPresent(pets::add);
        }
        s.setPets(pets);
        s.setActivities(scheduleDTO.getActivities());
        Schedule saved = scheduleRepository.save(s);
        ScheduleDTO out = new ScheduleDTO();
        out.setId(saved.getId());
        out.setDate(saved.getDate());
        out.setActivities(saved.getActivities());
        List<Long> eIds = new ArrayList<>();
        for (Employee e : saved.getEmployees()) eIds.add(e.getId());
        out.setEmployeeIds(eIds);
        List<Long> pIds = new ArrayList<>();
        for (Pet p : saved.getPets()) pIds.add(p.getId());
        out.setPetIds(pIds);
        return out;
    }

    public List<ScheduleDTO> getAllSchedules(){
        List<Schedule> all = scheduleRepository.findAll();
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule s : all) {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setId(s.getId());
            dto.setDate(s.getDate());
            dto.setActivities(s.getActivities());
            List<Long> eIds = new ArrayList<>();
            for (Employee e : s.getEmployees()) eIds.add(e.getId());
            dto.setEmployeeIds(eIds);
            List<Long> pIds = new ArrayList<>();
            for (Pet p : s.getPets()) pIds.add(p.getId());
            dto.setPetIds(pIds);
            res.add(dto);
        }
        return res;
    }

    public List<ScheduleDTO> getScheduleForPet(long petId){
        List<Schedule> list = scheduleRepository.findByPetsId(petId);
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule s : list) {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setId(s.getId());
            dto.setDate(s.getDate());
            dto.setActivities(s.getActivities());
            List<Long> eIds = new ArrayList<>();
            for (Employee e : s.getEmployees()) eIds.add(e.getId());
            dto.setEmployeeIds(eIds);
            List<Long> pIds = new ArrayList<>();
            for (Pet p : s.getPets()) pIds.add(p.getId());
            dto.setPetIds(pIds);
            res.add(dto);
        }
        return res;
    }

    public List<ScheduleDTO> getScheduleForEmployee(long employeeId){
        List<Schedule> list = scheduleRepository.findByEmployeesId(employeeId);
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule s : list) {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setId(s.getId());
            dto.setDate(s.getDate());
            dto.setActivities(s.getActivities());
            List<Long> eIds = new ArrayList<>();
            for (Employee e : s.getEmployees()) eIds.add(e.getId());
            dto.setEmployeeIds(eIds);
            List<Long> pIds = new ArrayList<>();
            for (Pet p : s.getPets()) pIds.add(p.getId());
            dto.setPetIds(pIds);
            res.add(dto);
        }
        return res;
    }

    public List<ScheduleDTO> getScheduleForCustomer(long customerId){
        List<Pet> pets = petRepository.findByOwnerId(customerId);
        LinkedHashSet<Schedule> found = new LinkedHashSet<>();
        for (Pet p : pets) {
            List<Schedule> schedules = scheduleRepository.findByPetsId(p.getId());
            found.addAll(schedules);
        }
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule s : found) {
            ScheduleDTO dto = new ScheduleDTO();
            dto.setId(s.getId());
            dto.setDate(s.getDate());
            dto.setActivities(s.getActivities());
            List<Long> eIds = new ArrayList<>();
            for (Employee e : s.getEmployees()) eIds.add(e.getId());
            dto.setEmployeeIds(eIds);
            List<Long> pIds = new ArrayList<>();
            for (Pet p : s.getPets()) pIds.add(p.getId());
            dto.setPetIds(pIds);
            res.add(dto);
        }
        return res;
    }
}
