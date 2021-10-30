package com.mylog.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity
@Table(name = "post_file_meta")
public class PostFileMeta {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @ManyToOne
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name="post_id")
    private Post post;

    @Column(name="origin_name")
    String originName;
    @Column(name="file_name")
    String fileName;
    @Column(name="file_size")
    Long fileSize;
    @Column(name="file_type")
    String fileType;
}
