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
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    /**
     * 속한 게시판
     */
    @ManyToOne
    @JoinColumn(name="series_id", nullable = false)
    private Series series;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "views", nullable = false)
    private long views;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
