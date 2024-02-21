package com.cat.model.dao;

import com.cat.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    @CreatedDate
    private Date createdAt ;
    @LastModifiedDate
    private Date updatedAt ;
    private UUID createdBy;
    private UUID updatedBy;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Category category;
    @ManyToOne
    private GPS gps;


}
