package com.cat.model.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="session_review")
public class SessionReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int ranking;
    @ManyToOne
    private GPS gps;
    private LocalDate date;
    private String type;
}
