package com.mylog.service;

import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentOutput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.response.PageResponse;
import com.mylog.response.Response;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<Response<Object>> createComment(CreateCommentInput createCommentInput);
    ResponseEntity<Response<SelectCommentOutput>> selectComment(int commentId);
    ResponseEntity<PageResponse<SelectCommentOutput>> selectCommentList(SelectCommentInput selectCommentInput);
    ResponseEntity<Response<Object>> updateComment( UpdateCommentInput updateCommentInput, int commentId);
    ResponseEntity<Response<Object>> deleteComment(int commentId);
}
