package com.mylog.service;

import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentInput;
import com.mylog.dto.comment.selectcomment.SelectCommentOutput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.dto.post.selectpost.SelectPostOutput;
import com.mylog.dto.series.createseries.CreateSeriesInput;
import com.mylog.dto.series.updateseries.UpdateSeriesInput;
import com.mylog.entity.Series;
import com.mylog.response.PageResponse;
import com.mylog.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SeriesService {
    ResponseEntity<Response<Object>> createSeries(CreateSeriesInput createSeriesInput);
    ResponseEntity<Response<Series>> selectSeries(int seriesId);
    ResponseEntity<Response<List<Series>>> selectSeriesList();
    ResponseEntity<Response<Object>> updateSeries(UpdateSeriesInput updateSeriesInput, int seriesId);
    ResponseEntity<Response<Object>> deleteSeries(int seriesId);
}
