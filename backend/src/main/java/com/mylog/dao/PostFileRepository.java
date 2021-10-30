package com.mylog.dao;

import com.mylog.entity.Post;
import com.mylog.entity.PostFileMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostFileRepository extends JpaRepository<PostFileMeta, Integer> {
    int countByPost(Post post);
    List<PostFileMeta> findByPost(Post post);
}