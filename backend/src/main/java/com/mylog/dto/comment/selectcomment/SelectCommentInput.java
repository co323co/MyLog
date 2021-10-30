package com.mylog.dto.comment.selectcomment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SelectCommentInput {
    private Integer postId;
    private int page;
    private int size;
}
