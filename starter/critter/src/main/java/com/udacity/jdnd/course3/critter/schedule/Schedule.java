package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToMany
    @JoinTable(name = "schedule_employee",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "schedule_pet",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private Set<Pet> pets = new HashSet<>();

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "schedule_activity", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "activity")
    private Set<EmployeeSkill> activities = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Set<Employee> getEmployees() { return employees; }
    public void setEmployees(Set<Employee> employees) { this.employees = employees; }

    public Set<Pet> getPets() { return pets; }
    public void setPets(Set<Pet> pets) { this.pets = pets; }

    public Set<EmployeeSkill> getActivities() { return activities; }
    public void setActivities(Set<EmployeeSkill> activities) { this.activities = activities; }
}
