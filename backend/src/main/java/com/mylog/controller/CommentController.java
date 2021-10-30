package com.mylog.controller;

import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentOutput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.response.PageResponse;
import com.mylog.response.Response;
import com.mylog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 CRUD API
 */
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록 API [POST] /api/comment
     *
     * @return ResponseEntity<Response<Object>>
     */
    // Body
    @PostMapping
    public ResponseEntity<Response<Object>> createComment(@RequestBody CreateCommentInput createCommentInput) {
        log.info("[POST] /api/comments");
        return commentService.createComment(createCommentInput);
    }

    /**
     * 댓글 조회 API
     * [GET] /api/comments?postId=&page=&size=
     * @return ResponseEntity<PageResponse<SelectCommentOutput>>
     */
    // Params
    @GetMapping
    public ResponseEntity<PageResponse<SelectCommentOutput>> getCommentList(SelectCommentInput selectCommentInput) {
        log.info("[GET] /api/comments?postId=&page=&size=");
        return commentService.selectCommentList(selectCommentInput);
    }

    /**
     * 댓글 수정 API
     * [PATCH] /api/comments/{id}
     * @return ResponseEntity<Response<Object>>
     */
    // Body
    @PatchMapping("/{id}")
    public ResponseEntity<Response<Object>> updateComment(@PathVariable("id") int id, @RequestBody UpdateCommentInput updateCommentInput) {
        log.info("[PATCH] /api/comments/" + id);
        return commentService.updateComment(updateCommentInput, id);
    }

    /**
     * 댓글 삭제 API
     * [DELETE] /api/comments/{id}
     * @return ResponseEntity<Response<Object>>
     */
    // Path-Variable
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deleteComment(@PathVariable("id") int id) {
        log.info("[DELETE] /api/comments/" + id);
        return commentService.deleteComment(id);
    }

}

