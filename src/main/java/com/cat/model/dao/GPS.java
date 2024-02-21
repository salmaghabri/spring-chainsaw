package com.cat.model.dao;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class GPS extends GPSTemplate{

    @ManyToOne
    private Employee employee;

}
