package com.mylog.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    /**
     * 댓글 달린 게시글
     */
    @ManyToOne
    @JoinColumn(name="post_id", nullable = false)
    private Post post;

    /**
     * 작성자 이름
     */
    @Column(name="writer")
    private String writer;
    /**
     * 수정시 사용할 비밀번호
     */
    @Column(name="password", nullable = false)
    private String password;

    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
