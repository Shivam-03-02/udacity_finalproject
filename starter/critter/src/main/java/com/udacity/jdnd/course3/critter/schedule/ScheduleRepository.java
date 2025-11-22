package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select distinct s from Schedule s left join fetch s.pets p left join fetch s.employees e where p.id = :petId")
    List<Schedule> findByPetsId(@Param("petId") Long petId);

    @Query("select distinct s from Schedule s left join fetch s.pets p left join fetch s.employees e where e.id = :employeeId")
    List<Schedule> findByEmployeesId(@Param("employeeId") Long employeeId);

    @Query("select distinct s from Schedule s left join fetch s.pets p left join fetch s.employees e")
    List<Schedule> findAllWithPetsAndEmployees();

    @Query("select distinct s from Schedule s left join fetch s.pets p left join fetch p.owner o left join fetch s.employees e where o.id = :customerId")
    List<Schedule> findByCustomerId(@Param("customerId") Long customerId);
}
