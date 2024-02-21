package com.cat.model.dao;

import com.cat.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private  UUID validator;
    private int ranking;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Goal goal;
    @ManyToOne
    private SessionReview sessionReview;
}
