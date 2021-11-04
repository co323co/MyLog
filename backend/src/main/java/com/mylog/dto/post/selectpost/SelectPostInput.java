package com.mylog.dto.post.selectpost;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SelectPostInput {
    private Integer seriesId;
    private Boolean hasSeries;
    private String search;
    private int page;
    private int size;
}
