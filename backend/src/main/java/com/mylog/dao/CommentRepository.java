package com.mylog.dao;

import com.mylog.entity.Comment;
import com.mylog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findByPost(Post post, Pageable paging);
}