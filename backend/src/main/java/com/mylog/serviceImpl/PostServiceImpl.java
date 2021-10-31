package com.mylog.serviceImpl;

import com.mylog.configuration.ValidationCheck;
import com.mylog.dao.CommentRepository;
import com.mylog.dao.PostRepository;
import com.mylog.dao.SeriesRepository;
import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentOutput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.dto.post.createpost.CreatePostInput;
import com.mylog.dto.post.selectpost.SelectPostInput;
import com.mylog.dto.post.selectpost.SelectPostOutput;
import com.mylog.dto.post.updatepost.UpdatePostInput;
import com.mylog.entity.Comment;
import com.mylog.entity.Post;
import com.mylog.entity.Series;
import com.mylog.response.PageResponse;
import com.mylog.response.Response;
import com.mylog.service.CommentService;
import com.mylog.service.PostService;
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

@Service("PostService")
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final SeriesRepository seriesRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> createPost(CreatePostInput createPostInput) {
        System.out.println(createPostInput);
        // 1. 값 형식 체크
        if (createPostInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValid(createPostInput.getTitle()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValid(createPostInput.getContent())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_CONTENTS));
        }
        // 2. 게시글 생성
        Post post;
        try {
            Series series = null;
            if(createPostInput.getSeriesId()!=null){
                series = seriesRepository.findById(createPostInput.getSeriesId()).orElse(null);
                if(series==null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response<>(NOT_FOUND_SERIES));
                }
            }
            post = Post.builder()
                    .series(series)
                    .title(createPostInput.getTitle())
                    .content(createPostInput.getContent())
                    .build();
            postRepository.save(post);
        } catch (Exception e) {
            log.error("[posts/post] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(null, CREATED_POST));
    }

    @Override
    public ResponseEntity<PageResponse<SelectPostOutput>> selectPostList(SelectPostInput selectPostInput) {
        // 1. 값 형식 체크
        if (selectPostInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));
        if (!ValidationCheck.isValidPage(selectPostInput.getPage())
                || !ValidationCheck.isValidId(selectPostInput.getSize()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));

        // 2. 게시글 조회
        Pageable pageable = PageRequest.of(selectPostInput.getPage() - 1, selectPostInput.getSize(),
                Sort.Direction.DESC,"createdAt");
        Page<SelectPostOutput> responseList;
        try {
            responseList = postRepository.findByDynamicQuery(selectPostInput,pageable);
        } catch (Exception e) {
            log.error("[posts/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PageResponse<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(responseList, SUCCESS_SELECT_POST));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> updatePost(UpdatePostInput updatePostInput, int postId) {
        try {
            // 1. 값 형식 체크
            if (updatePostInput == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NO_VALUES));

            // 2. 게시글 조회
            Post post = postRepository.findById(postId).orElse(null);
            if (post == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NOT_FOUND_POST));

            // 3. 댓글 수정
            //시리즈 수정
            if(updatePostInput.getSeriesId()!=null){
                Series series = seriesRepository.findById(updatePostInput.getSeriesId()).orElse(null);
                if(series==null)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response<>(NOT_FOUND_SERIES));
                post.setSeries(series);
            }
            //제목 수정
            if(updatePostInput.getTitle()!=null)
                post.setTitle(updatePostInput.getTitle());

            //내용 수정
            if(updatePostInput.getContent()!=null)
                post.setContent(updatePostInput.getContent());
            postRepository.save(post);
        } catch (Exception e) {
            log.error("[posts/patch] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_UPDATE_POST));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> deletePost(int postId) {
        try {
            // 1. 게시글 조회
            Post post = postRepository.findById(postId).orElse(null);

            // 2. 게시글 삭제
            if (post == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NOT_FOUND_POST));

            postRepository.delete(post);

        } catch (Exception e) {
            log.error("[posts/delete] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_DELETE_POST));
    }
}
