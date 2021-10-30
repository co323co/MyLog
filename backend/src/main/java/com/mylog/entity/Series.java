package com.mylog.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity
@Table(name = "series")
public class Series {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(name="name", nullable = false)
    private String name;
}
