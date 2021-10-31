package com.mylog.dto.post.createpost;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CreatePostInput {
    private Integer seriesId;
    private String title;
    private String content;
}
