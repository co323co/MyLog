package com.mylog.dto.post.selectpost;

import com.mylog.dto.postfile.SelectPostFileMetaOutput;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class SelectPostOutput {
    private Integer seriesId;
    private String seriesName;
    private Integer postId;
    private String postTitle;
    private String postContent;
    private LocalDateTime commentCreatedAt;
    private LocalDateTime commentUpdatedAt;
    private Integer commentCount;

    @QueryProjection
    public SelectPostOutput(Integer seriesId, String seriesName, Integer postId, String postTitle, String postContent, LocalDateTime commentCreatedAt, LocalDateTime commentUpdatedAt, Integer commentCount) {
        this.seriesId = seriesId;
        this.seriesName = seriesName;
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.commentCreatedAt = commentCreatedAt;
        this.commentUpdatedAt = commentUpdatedAt;
        this.commentCount = commentCount;
    }
}
