package com.mylog.dto.comment.updatecomment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateCommentInput {
    private String writer;
    private String password;
    private String content;
}
