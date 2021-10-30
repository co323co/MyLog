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
    private LocalDateTime postCreatedAt;
    private LocalDateTime postUpdatedAt;
    private Integer commentCount;

    @QueryProjection
    public SelectPostOutput(Integer seriesId, String seriesName, Integer postId, String postTitle, String postContent, LocalDateTime postCreatedAt, LocalDateTime postUpdatedAt, Integer commentCount) {
        this.seriesId = seriesId;
        this.seriesName = seriesName;
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postCreatedAt = postCreatedAt;
        this.postUpdatedAt = postUpdatedAt;
        this.commentCount = commentCount;
    }
}
