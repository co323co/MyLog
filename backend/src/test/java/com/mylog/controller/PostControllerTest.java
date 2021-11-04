package com.mylog.controller;

import com.mylog.ApiDocumentationTest;
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

public class PostControllerTest extends ApiDocumentationTest {

    @DisplayName("게시글 작성 - 모든 유효성 검사에 통과했다면 게시글 작성 성공")
    @Test
    public void 게시글_작성() throws Exception {
        //given
        CreatePostInput createPostInput = CreatePostInput
                .builder()
                .seriesId(6)
                .title("새로운 게시글")
                .content("반가워요!")
                .build();

        //when
        ResultActions result = mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andDo(
                        document(
                                "posts/create/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("seriesId").type(JsonFieldType.NUMBER)
                                                .description("시리즈 번호")
                                                .attributes(key("constraint")
                                                        .value(1)),
                                        fieldWithPath("title").type(JsonFieldType.STRING)
                                                .description("게시글 제목")
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요.")),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("게시글 내용")
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요."))
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

    @DisplayName("게시글 리스트 조회")
    @Test
    public void 게시글_조회() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/posts/")
                        .queryParam("seriesId", "6")
                        .queryParam("hasSeries", "")
                        .queryParam("search", "")
                        .queryParam("size", "10")
                        .queryParam("page", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "posts/select/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestParameters(
                                        parameterWithName("seriesId").description("시리즈 번호").optional(),
                                        parameterWithName("hasSeries").description("시리즈 유무").optional(),
                                        parameterWithName("search").description("제목/내용 검색어").optional(),
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
                                                .description("게시글 조회 결과 리스트").optional(),
                                        fieldWithPath("result.[].seriesId").type(JsonFieldType.NUMBER)
                                                .description("시리즈 번호"),
                                        fieldWithPath("result.[].seriesName").type(JsonFieldType.STRING)
                                                .description("시리즈 이름"),
                                        fieldWithPath("result.[].postId").type(JsonFieldType.NUMBER)
                                                .description("게시글 번호"),
                                        fieldWithPath("result.[].postTitle").type(JsonFieldType.STRING)
                                                .description("게시글 제목"),
                                        fieldWithPath("result.[].postContent").type(JsonFieldType.STRING)
                                                .description("게시글 내용"),
                                        fieldWithPath("result.[].postCreatedAt").type(JsonFieldType.STRING)
                                                .description("게시글 작성일"),
                                        fieldWithPath("result.[].postUpdatedAt").type(JsonFieldType.STRING)
                                                .description("게시글 수정일"),
                                        fieldWithPath("result.[].commentCount").type(JsonFieldType.NUMBER)
                                                .description("댓글 수"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }


    @DisplayName("게시글 수정 - 모든 유효성 검사에 통과했다면 게시글 수정 성공")
    @Test
    public void 게시글_수정() throws Exception {
        //given
        UpdatePostInput updatePostInput = UpdatePostInput
                .builder()
                .seriesId(6)
                .title("수정된 게시글")
                .content("수정됐어요!")
                .build();

        //when
        ResultActions result = mockMvc.perform(patch("/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "posts/update/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("id").description("수정 요청할 게시글 ID")
                                ),
                                requestFields(
                                        fieldWithPath("seriesId").type(JsonFieldType.NUMBER)
                                                .description("시리즈 번호 (없는 번호일 경우 미분류)")
                                                .optional()
                                                .attributes(key("constraint")
                                                        .value("없는 번호일 경우 미분류")),
                                        fieldWithPath("title").type(JsonFieldType.STRING)
                                                .description("게시글 제목")
                                                .optional()
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요.")),
                                        fieldWithPath("content").type(JsonFieldType.STRING)
                                                .description("게시글 내용")
                                                .optional()
                                                .attributes(key("constraint")
                                                        .value("최소 1글자 이상으로 입력해주세요."))

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

    @DisplayName("게시글 삭제 - 모든 유효성 검사에 통과했다면 게시글 삭제 성공")
    @Test
    public void 게시글_삭제() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete("/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "posts/delete/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("id").description("삭제 요청할 게시글 ID")
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
