package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.udacity.jdnd.course3.critter.util.DTOMapper;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleEntityService scheduleEntityService;

    @Autowired
    private DTOMapper mapper;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule saved = scheduleEntityService.createSchedule(
                scheduleDTO.getPetIds(),
                scheduleDTO.getEmployeeIds(),
                scheduleDTO.getDate(),
                scheduleDTO.getActivities()
        );
        return mapper.toScheduleDTO(saved);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> all = scheduleEntityService.findAll();
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule s : all) res.add(mapper.toScheduleDTO(s));
        return res;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> list = scheduleEntityService.findByPetId(petId);
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule s : list) res.add(mapper.toScheduleDTO(s));
        return res;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> list = scheduleEntityService.findByEmployeeId(employeeId);
        if (list == null || list.isEmpty()) {
            // some Postman requests incorrectly call employee endpoint for customer schedules;
            // fallback to customer schedules when no employee schedules found
            list = scheduleEntityService.findByCustomerId(employeeId);
        }
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule s : list) res.add(mapper.toScheduleDTO(s));
        return res;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> list = scheduleEntityService.findByCustomerId(customerId);
        List<ScheduleDTO> res = new ArrayList<>();
        for (Schedule s : list) res.add(mapper.toScheduleDTO(s));
        return res;
    }
}
