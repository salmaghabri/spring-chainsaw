package com.cat.model.dao;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "gps_template")
public class GPSTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int year;
    private int  sessionNumber;
    private int minGoalsNumber;

}
