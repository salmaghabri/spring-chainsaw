package com.cat.model.dao;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String email;
    private int hierarchy;
    private String profilePictureURL;
    @ManyToOne
    private Job job;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Supervisor> supervisors;
    @ManyToMany(mappedBy = "employees")
    private List<Manager> managers;




}
