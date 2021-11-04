package com.mylog.controller;

import com.mylog.dto.post.createpost.CreatePostInput;
import com.mylog.dto.post.updatepost.UpdatePostInput;
import com.mylog.dto.series.createseries.CreateSeriesInput;
import com.mylog.dto.series.updateseries.UpdateSeriesInput;
import com.mylog.entity.Series;
import com.mylog.response.Response;
import com.mylog.service.SeriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 시리즈 CRUD API
 */
@RestController
@RequestMapping("/series")
@RequiredArgsConstructor
@Slf4j
public class SeriesController {

    private final SeriesService seriesService;

    /**
     * 시리즈 등록 API [POST] /api/series
     *
     * @return ResponseEntity<Response<Object>>
     */
    // Body
    @PostMapping
    public ResponseEntity<Response<Object>> createSeries(@RequestBody CreateSeriesInput createSeriesInput) {
        log.info("[POST] /api/posts");
        return seriesService.createSeries(createSeriesInput);
    }

    /**
     * 시리즈 조회 API
     * [GET] /api/series/{id}
     * @return ResponseEntity<Response<Series>>
     */
    // Params
    @GetMapping("/{id}")
    public ResponseEntity<Response<Series>> getSeries(@PathVariable("id") int id) {
        log.info("[GET] /api/series/{id}");
        return seriesService.selectSeries(id);
    }

    /**
     * 시리즈 리스트 조회 API
     * [GET] /api/series
     * @return ResponseEntity<Response<List<Series>>>
     */
    // Params
    @GetMapping
    public ResponseEntity<Response<List<Series>>> getSeriesList() {
        log.info("[GET] /api/series");
        return seriesService.selectSeriesList();
    }

    /**
     * 시리즈 수정 API
     * [PATCH] /api/series/{id}
     * @return ResponseEntity<Response<Object>>
     */
    // Body
    @PatchMapping("/{id}")
    public ResponseEntity<Response<Object>> updateSeries(@PathVariable("id") int id, @RequestBody UpdateSeriesInput updateSeriesInput) {
        log.info("[PATCH] /api/series/" + id);
        return seriesService.updateSeries(updateSeriesInput, id);
    }

    /**
     * 시리즈 삭제 API
     * [DELETE] /api/series/{id}
     * @return ResponseEntity<Response<Object>>
     */
    // Path-Variable
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deleteSeries(@PathVariable("id") int id) {
        log.info("[DELETE] /api/series/" + id);
        return seriesService.deleteSeries(id);
    }

}

