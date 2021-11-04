package com.mylog.dto.post.updatepost;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdatePostInput {
    private Integer seriesId;
    private String title;
    private String content;
}
