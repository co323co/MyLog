package com.mylog.controller;

import com.mylog.dto.comment.selectcomment.SelectCommentOutput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.dto.post.createpost.CreatePostInput;
import com.mylog.dto.post.selectpost.SelectPostInput;
import com.mylog.dto.post.selectpost.SelectPostOutput;
import com.mylog.dto.post.updatepost.UpdatePostInput;
import com.mylog.response.PageResponse;
import com.mylog.response.Response;
import com.mylog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 게시글 CRUD API
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    /**
     * 게시글 등록 API [POST] /api/posts
     *
     * @return ResponseEntity<Response<Object>>
     */
    // Body
    @PostMapping
    public ResponseEntity<Response<Object>> createPost(@RequestBody CreatePostInput createPostInput) {
        log.info("[POST] /api/posts");
        return postService.createPost(createPostInput);
    }

    /**
     * 게시글 조회 API
     * [GET] /api/posts?seriesId=&hasSeries=&search=&page=&size=
     * @return ResponseEntity<PageResponse<SelectCommentOutput>>
     */
    // Params
    @GetMapping
    public ResponseEntity<PageResponse<SelectPostOutput>> getPostList(SelectPostInput selectPostInput) {
        log.info("[GET] /api/posts?seriesId=&hasSeries=&search=&page=&size=");
        return postService.selectPostList(selectPostInput);
    }

    /**
     * 게시글 수정 API
     * [PATCH] /api/posts/{id}
     * @return ResponseEntity<Response<Object>>
     */
    // Body
    @PatchMapping("/{id}")
    public ResponseEntity<Response<Object>> updatePost(@PathVariable("id") int id, @RequestBody UpdatePostInput updatePostInput) {
        log.info("[PATCH] /api/posts/" + id);
        return postService.updatePost(updatePostInput, id);
    }

    /**
     * 게시글 삭제 API
     * [DELETE] /api/posts/{id}
     * @return ResponseEntity<Response<Object>>
     */
    // Path-Variable
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deletePost(@PathVariable("id") int id) {
        log.info("[DELETE] /api/posts/" + id);
        return postService.deletePost(id);
    }

}

