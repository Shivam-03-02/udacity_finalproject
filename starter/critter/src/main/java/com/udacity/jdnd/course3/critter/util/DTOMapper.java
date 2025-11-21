package com.udacity.jdnd.course3.critter.util;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DTOMapper {

    public PetDTO toPetDTO(Pet p){
        if (p == null) return null;
        PetDTO dto = new PetDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setType(p.getType());
        dto.setBirthDate(p.getBirthDate());
        dto.setNotes(p.getNotes());
        dto.setOwnerId(p.getOwner() != null ? p.getOwner().getId() : 0);
        return dto;
    }

    public Pet toPetEntity(PetDTO dto){
        if (dto == null) return null;
        Pet p = new Pet();
        if (dto.getId() > 0) p.setId(dto.getId());
        p.setName(dto.getName());
        p.setType(dto.getType());
        p.setBirthDate(dto.getBirthDate());
        p.setNotes(dto.getNotes());
        return p;
    }

    public CustomerDTO toCustomerDTO(Customer c){
        if (c == null) return null;
        CustomerDTO dto = new CustomerDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setPhoneNumber(c.getPhoneNumber());
        dto.setNotes(c.getNotes());
        List<Long> petIds = new ArrayList<>();
        if (c.getPets() != null) for (com.udacity.jdnd.course3.critter.pet.Pet p : c.getPets()) petIds.add(p.getId());
        dto.setPetIds(petIds);
        return dto;
    }

    public EmployeeDTO toEmployeeDTO(Employee e){
        if (e == null) return null;
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setSkills(e.getSkills());
        dto.setDaysAvailable(e.getDaysAvailable());
        return dto;
    }

    public ScheduleDTO toScheduleDTO(Schedule s){
        if (s == null) return null;
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(s.getId());
        dto.setDate(s.getDate());
        dto.setActivities(s.getActivities());
        List<Long> eIds = new ArrayList<>();
        if (s.getEmployees() != null) for (com.udacity.jdnd.course3.critter.user.Employee e : s.getEmployees()) eIds.add(e.getId());
        dto.setEmployeeIds(eIds);
        List<Long> pIds = new ArrayList<>();
        if (s.getPets() != null) for (com.udacity.jdnd.course3.critter.pet.Pet p : s.getPets()) pIds.add(p.getId());
        dto.setPetIds(pIds);
        return dto;
    }
}
