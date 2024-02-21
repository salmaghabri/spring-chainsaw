package com.cat.model.dao;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class GoalReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int progress;
    @CreatedDate
    private Date createdAt;
    private UUID createdBy;
    @ManyToOne
    private Goal goal;
    private String feedback;
    @ManyToOne
    private SessionReview sessionReview;
}
