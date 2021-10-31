package com.mylog.serviceImpl;

import com.mylog.configuration.ValidationCheck;
import com.mylog.dao.CommentRepository;
import com.mylog.dao.PostRepository;
import com.mylog.dao.SeriesRepository;
import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentOutput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.dto.series.createseries.CreateSeriesInput;
import com.mylog.dto.series.updateseries.UpdateSeriesInput;
import com.mylog.entity.Comment;
import com.mylog.entity.Post;
import com.mylog.entity.Series;
import com.mylog.response.PageResponse;
import com.mylog.response.Response;
import com.mylog.service.CommentService;
import com.mylog.service.SeriesService;
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

import java.util.List;

import static com.mylog.response.ResponseStatus.*;

@Service("SeriesService")
@RequiredArgsConstructor
@Slf4j
public class SeriesServiceImpl implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> createSeries(CreateSeriesInput createSeriesInput) {
        // 1. 값 형식 체크
        if (createSeriesInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValid(createSeriesInput.getName()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        // 2. 시리즈 생성
        Series series;
        try {
            seriesRepository.save(Series.builder()
                            .name(createSeriesInput.getName())
                            .build());
        } catch (Exception e) {
            log.error("[series/post] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(null, CREATED_SERIES));
    }

    @Override
    public ResponseEntity<Response<Series>> selectSeries(int seriesId) {
        // 2. 시리즈 조회
        Series series;
        try {
            series = seriesRepository.findById(seriesId).orElse(null);
            if(series==null)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response<>(NOT_FOUND_SERIES));
        } catch (Exception e) {
            log.error("[series/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(series, SUCCESS_SELECT_SERIES));
    }

    @Override
    public ResponseEntity<Response<List<Series>>> selectSeriesList() {
        // 2. 시리즈 조회
        List<Series> seriesList;
        try {
            seriesList = seriesRepository.findAll();
        } catch (Exception e) {
            log.error("[series/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(seriesList, SUCCESS_SELECT_SERIES));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> updateSeries(UpdateSeriesInput updateSeriesInput, int seriesId) {
        try {

            // 1. 값 형식 체크
            if (updateSeriesInput == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NO_VALUES));
            if (StringUtils.isBlank(updateSeriesInput.getName()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NO_VALUES));

            // 2. 시리즈 조회
            Series series = seriesRepository.findById(seriesId).orElse(null);
            if (series == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NOT_FOUND_SERIES));
            }
            // 3. 시리즈 수정
            //시리즈명 수정
            series.setName(updateSeriesInput.getName());
            seriesRepository.save(series);
        } catch (Exception e) {
            log.error("[series/patch] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }
        // 5. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_UPDATE_SERIES));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> deleteSeries(int seriesId) {
        try {
            // 1. 시리즈 조회
            Series series = seriesRepository.findById(seriesId).orElse(null);

            // 2. 시리즈 삭제
            if (series == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(NOT_FOUND_SERIES));
            List<Post> postList = postRepository.findBySeries(series);
            for(Post post : postList)
                post.setSeries(null);
            postRepository.saveAll(postList);
            seriesRepository.delete(series);

        } catch (Exception e) {
            log.error("[series/delete] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_DELETE_SERIES));
    }
}
