package com.mylog.dto.post.updatepost;

import com.mylog.dto.postfile.SelectPostFileMetaOutput;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdatePostInput {
    private Integer SeriesId;
    private String title;
    private String content;
//    private List<SelectPostFileMetaOutput> filesMeta;
}
