package com.mylog.controller;

import com.mylog.ApiDocumentationTest;
import com.mylog.dto.comment.createcomment.CreateCommentInput;
import com.mylog.dto.comment.updatecomment.UpdateCommentInput;
import com.mylog.dto.series.createseries.CreateSeriesInput;
import com.mylog.dto.series.updateseries.UpdateSeriesInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.mylog.ApiDocumentUtils.getDocumentRequest;
import static com.mylog.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SeriesControllerTest extends ApiDocumentationTest {

    @DisplayName("시리즈 작성 - 모든 유효성 검사에 통과했다면 시리즈 작성 성공")
    @Test
    public void 시리즈_작성() throws Exception {
        //given
        CreateSeriesInput createSeriesInput = CreateSeriesInput
                .builder()
                .name("새로운 시리즈")
                .build();

        //when
        ResultActions result = mockMvc.perform(post("/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSeriesInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isCreated())
                .andDo(
                        document(
                                "series/create/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING)
                                                .description("시리즈 이름")
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

    @DisplayName("시리즈 조회")
    @Test
    public void 시리즈_조회() throws Exception {
        //given
        //when
        ResultActions result = mockMvc.perform(get("/series/{id}",6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "series_detail/select/successful",
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
                                        fieldWithPath("result.id").type(JsonFieldType.NUMBER)
                                                .description("시리즈 번호"),
                                        fieldWithPath("result.name").type(JsonFieldType.STRING)
                                                .description("시리즈 이름"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }

    @DisplayName("시리즈 리스트 조회")
    @Test
    public void 시리즈_리스트_조회() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(get("/series/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "series/select/successful",
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
                                        fieldWithPath("result").type(JsonFieldType.ARRAY)
                                                .description("시리즈 조회 결과 리스트").optional(),
                                        fieldWithPath("result.[].id").type(JsonFieldType.NUMBER)
                                                .description("시리즈 번호"),
                                        fieldWithPath("result.[].name").type(JsonFieldType.STRING)
                                                .description("시리즈 이름"),
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING)
                                                .description("api 호출 일시")
                                )
                        ));
    }


    @DisplayName("시리즈 수정 - 모든 유효성 검사에 통과했다면 시리즈 수정 성공")
    @Test
    public void 시리즈_수정() throws Exception {
        //given
        UpdateSeriesInput updateSeriesInput = UpdateSeriesInput
                .builder()
                .name("수정된 시리즈")
                .build();

        //when
        ResultActions result = mockMvc.perform(patch("/series/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSeriesInput)).accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "series/update/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("id").description("수정 요청할 시리즈 ID")
                                ),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING)
                                                .description("시리즈 이름")
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

    @DisplayName("시리즈 삭제 - 모든 유효성 검사에 통과했다면 시리즈 삭제 성공")
    @Test
    public void 시리즈_삭제() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete("/series/{id}", 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        //then
        result.andExpect(status().isOk())
                .andDo(
                        document(
                                "series/delete/successful",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                pathParameters(
                                        parameterWithName("id").description("삭제 요청할 시리즈 ID")
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
