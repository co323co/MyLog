package com.mylog.controller;

import com.mylog.ApiDocumentationTest;
import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.dto.post.createpost.CreatePostInput;
import com.mylog.dto.post.updatepost.UpdatePostInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.mylog.ApiDocumentUtils.getDocumentRequest;
import static com.mylog.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends ApiDocumentationTest {

    @DisplayName("댓글 작성 - 모든 유효성 검사에 통과했다면 댓글 작성 성공")
    @Test
    public void 댓글_작성() throws Exception {
        //given
        CreateCommentInput createCommentInput = CreateCommentInput
                .builder()
                .postId(1)
                .writer("홍길동")
                .content("새로운 댓글")
                .password("1234")
                .build();

        //when
        ResultActions result = mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createCommentInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andDo(
                        document(
                                "comments/create/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER)
                                                .description("게시글 번호")
                                                .attributes(key("constraint")
                                                        .value(1)),
                                        fieldWithPath("writer").type(JsonFieldType.STRING)
                                                .description("작성자 닉네임 (없을 경우 익명)")
                                                .attributes(key("constraint")
                                                        .value("없을 경우 익명"))
                                                .optional(),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("댓글 내용")
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요.")),
                                        fieldWithPath("password").type(JsonFieldType.STRING)
                                                .description("댓글 비밀번호")
                                                .attributes(key("constraint")
                                                        .value("4자리 숫자"))
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("댓글 조회")
    @Test
    public void 댓글_조회() throws Exception {
        //given
        //when
        ResultActions result = mockMvc.perform(get("/comments/{id}",5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "comments_detail/select/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("result.commentId").type(JsonFieldType.NUMBER)
                                                .description("댓글 번호"),
                                        fieldWithPath("result.postId").type(JsonFieldType.NUMBER)
                                                .description("게시글 번호"),
                                        fieldWithPath("result.commentWriter").type(JsonFieldType.STRING)
                                                .description("댓글 작성자"),
                                        fieldWithPath("result.commentContent").type(JsonFieldType.STRING)
                                                .description("댓글 내용"),
                                        fieldWithPath("result.commentCreatedAt").type(JsonFieldType.STRING)
                                                .description("댓글 작성일"),
                                        fieldWithPath("result.commentUpdatedAt").type(JsonFieldType.STRING)
                                                .description("댓글 수정일"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("댓글 리스트 조회")
    @Test
    public void 댓글_리스트_조회() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/comments/")
                        .queryParam("postId", "1")
                        .queryParam("size", "10")
                        .queryParam("page", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "comments/select/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("postId").description("게시글 번호").optional(),
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 사이즈")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("page.currentPage").type(JsonFieldType.NUMBER)
                                                .description("현재 페이지 번호"),
                                        fieldWithPath("page.pageSize").type(JsonFieldType.NUMBER)
                                                .description("페이지 사이즈"),
                                        fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER)
                                                .description("전체 페이지 수"),
                                        fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER)
                                                .description("전체 요소 수"),
                                        fieldWithPath("result").type(JsonFieldType.ARRAY)
                                                .description("댓글 조회 결과 리스트").optional(),
                                        fieldWithPath("result.[].commentId").type(JsonFieldType.NUMBER)
                                                .description("댓글 번호"),
                                        fieldWithPath("result.[].postId").type(JsonFieldType.NUMBER)
                                                .description("게시글 번호"),
                                        fieldWithPath("result.[].commentWriter").type(JsonFieldType.STRING)
                                                .description("댓글 작성자"),
                                        fieldWithPath("result.[].commentContent").type(JsonFieldType.STRING)
                                                .description("댓글 내용"),
                                        fieldWithPath("result.[].commentCreatedAt").type(JsonFieldType.STRING)
                                                .description("댓글 작성일"),
                                        fieldWithPath("result.[].commentUpdatedAt").type(JsonFieldType.STRING)
                                                .description("댓글 수정일"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }


    @DisplayName("댓글 수정 - 모든 유효성 검사에 통과했다면 댓글 수정 성공")
    @Test
    public void 댓글_수정() throws Exception {
        //given
        UpdateCommentInput updateCommentInput = UpdateCommentInput
                .builder()
                .writer("코코")
                .content("수정된 댓글")
                .password("1234")
                .build();

        //when
        ResultActions result = mockMvc.perform(patch("/comments/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCommentInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "comments/update/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("id").description("수정 요청할 댓글 ID")
                                ),
                                requestFields(
                                        fieldWithPath("writer").type(JsonFieldType.STRING)
                                                .description("작성자 (없는 경우 익명)")
                                                .optional()
                                                .attributes(key("constraint")
                                                        .value("없는 경우 익명")),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("댓글 내용")
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요.")),
                                        fieldWithPath("password").type(JsonFieldType.STRING)
                                                .description("댓글 비밀번호")
                                                .attributes(key("constraint")
                                                        .value("4자리 숫자"))

                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("댓글 삭제 - 모든 유효성 검사에 통과했다면 댓글 삭제 성공")
    @Test
    public void 댓글_삭제() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete("/comments/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "comments/delete/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("id").description("삭제 요청할 댓글 ID")
                                ),
                                responseFields(
                                        fieldWithPath("isSuccess").type(JsonFieldType.BOOLEAN)
                                                .description("요청 성공 여부"),
                                        fieldWithPath("status").type(JsonFieldType.NUMBER)
                                                .description("응답 상태"),
                                        fieldWithPath("code").type(JsonFieldType.NUMBER)
                                                .description("응답 코드"),
                                        fieldWithPath("message").type(JsonFieldType.STRING)
                                                .description("응답 메시지"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

}
