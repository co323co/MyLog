package com.mylog.service;

import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentOutput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.dto.post.createpost.CreatePostInput;
import com.mylog.dto.post.selectpost.SelectPostInput;
import com.mylog.dto.post.selectpost.SelectPostOutput;
import com.mylog.dto.post.updatepost.UpdatePostInput;
import com.mylog.response.PageResponse;
import com.mylog.response.Response;
import org.springframework.http.ResponseEntity;

public interface PostService {
    ResponseEntity<Response<Object>> createPost(CreatePostInput createPostInput);
    ResponseEntity<PageResponse<SelectPostOutput>> selectPostList(SelectPostInput selectPostInput);
    ResponseEntity<Response<Object>> updatePost(UpdatePostInput updatePostInput, int postId);
    ResponseEntity<Response<Object>> deletePost(int postId);
}
