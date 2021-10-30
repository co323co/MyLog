package com.mylog.serviceImpl;

import com.mylog.configuration.ValidationCheck;
import com.mylog.dao.CommentRepository;
import com.mylog.dao.PostRepository;
import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentOutput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.entity.Comment;
import com.mylog.entity.Post;
import com.mylog.response.PageResponse;
import com.mylog.response.Response;
import com.mylog.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mylog.response.ResponseStatus.*;

@Service("CommentService")
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> createComment(CreateCommentInput createCommentInput) {
        // 1. 값 형식 체크
        if (createCommentInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValidId(createCommentInput.getPostId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_ID_VALUE));
        if (!ValidationCheck.isValid(createCommentInput.getContent()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_CONTENTS));
        if (!ValidationCheck.isValid(createCommentInput.getPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        // 2. 댓글 생성
        Comment comment;
        try {
            Post post = postRepository.findById(createCommentInput.getPostId()).orElse(null);
            String writer = StringUtils.isNoneBlank(createCommentInput.getWriter())?createCommentInput.getWriter():"익명";
            if(post==null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NOT_FOUND_POST));
            comment = Comment.builder()
                    .content(createCommentInput.getContent())
                    .writer(writer)
                    .password(createCommentInput.getPassword())
                    .post(post)
                    .build();
            commentRepository.save(comment);
        } catch (Exception e) {
            log.error("[comments/post] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(null, CREATED_COMMENT));
    }

    @Override
    public ResponseEntity<PageResponse<SelectCommentOutput>> selectCommentList(SelectCommentInput selectCommentInput) {
        // 1. 값 형식 체크
        if (selectCommentInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));
        if (!ValidationCheck.isValidPage(selectCommentInput.getPage())
                || !ValidationCheck.isValidId(selectCommentInput.getSize()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));

        // 2. 댓글 조회
        Pageable pageable = PageRequest.of(selectCommentInput.getPage() - 1, selectCommentInput.getSize(),
                Sort.Direction.DESC,"createdAt");
        Page<SelectCommentOutput> responseList;
        Page<Comment> commentList;
        try {
            if(selectCommentInput.getPostId()==null)
                commentList = commentRepository.findAll(pageable);
            else{
                Post post = postRepository.findById(selectCommentInput.getPostId()).orElse(null);
                if(post==null)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new PageResponse<>(NOT_FOUND_POST));
                commentList = commentRepository.findByPost(post, pageable);
            }
        } catch (Exception e) {
            log.error("[comments/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PageResponse<>(DATABASE_ERROR));
        }

        // 최종 출력값 정리
        responseList = commentList.map(comment -> SelectCommentOutput.builder()
                .postId(comment.getPost().getId())
                .commentWriter(comment.getWriter())
                .commentContent(comment.getContent())
                .commentCreatedAt(comment.getCreatedAt())
                .commentUpdatedAt(comment.getUpdatedAt())
                .build());

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(responseList, SUCCESS_SELECT_COMMENT));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> updateComment(UpdateCommentInput updateCommentInput, int commentId) {
        try {

            // 1. 값 형식 체크
            if (updateCommentInput == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NO_VALUES));
            if (StringUtils.isBlank(updateCommentInput.getPassword()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NO_VALUES));

            // 2. 댓글 조회
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NOT_FOUND_COMMENT));
            }
            // 2. 비밀번호 일치 확인
            if (!comment.getPassword().equals(updateCommentInput.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(BAD_PASSWORD_VALUE));
            }
            // 3. 댓글 수정
            //작성자명 수정
            if(updateCommentInput.getWriter()!=null){
                String writer = updateCommentInput.getWriter().equals("")?"익명":updateCommentInput.getWriter();
                comment.setWriter(writer);
            }
            //댓글 내용 수정
            if(updateCommentInput.getContent()!=null)
                comment.setContent(updateCommentInput.getContent());
            commentRepository.save(comment);
        } catch (Exception e) {
            log.error("[comments/patch] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_UPDATE_COMMENT));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> deleteComment(int commentId) {
        try {
            // 1. 댓글 조회
            Comment comment = commentRepository.findById(commentId).orElse(null);

            // 2. 댓글 삭제
            if (comment == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(BAD_ID_VALUE));

            commentRepository.delete(comment);

        } catch (Exception e) {
            log.error("[comments/delete] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_DELETE_COMMENT));
    }
}
