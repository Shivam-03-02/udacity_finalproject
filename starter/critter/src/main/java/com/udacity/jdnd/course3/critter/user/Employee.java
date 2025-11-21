package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill")
    private Set<EmployeeSkill> skills = new HashSet<>();

    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_availability", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "day_of_week")
    private Set<DayOfWeek> daysAvailable = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<EmployeeSkill> getSkills() { return skills; }
    public void setSkills(Set<EmployeeSkill> skills) { this.skills = skills; }

    public Set<DayOfWeek> getDaysAvailable() { return daysAvailable; }
    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) { this.daysAvailable = daysAvailable; }
}
