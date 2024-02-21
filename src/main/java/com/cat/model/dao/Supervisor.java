package com.cat.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Supervisor extends Employee {

    @ManyToMany
    private List<Employee> employees;
}
