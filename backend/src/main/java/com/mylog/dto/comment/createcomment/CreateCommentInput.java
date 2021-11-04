package com.mylog.dto.comment.createcomment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateCommentInput {
    private int postId; //댓글을 단 게시글 id
    private String writer; //작성자 이름
    private String password; //패스워드
    private String content; //내용
}
