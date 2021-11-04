package com.mylog.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 HTTP 에러 코드
 */
@AllArgsConstructor
@Getter
public enum ResponseStatus {
    /*
     * 2XX Success
     */
    // 200 OK - 클라이언트의 요청을 서버가 정상적으로 처리했다.
    SUCCESS(200, 200, "요청에 성공하였습니다."), SUCCESS_SELECT_COMMENT(200, 201, "댓글 조회에 성공하였습니다."),
    SUCCESS_SELECT_POST(200, 202, "게시글 조회에 성공하였습니다."), SUCCESS_SELECT_SERIES(200, 204, "시리즈 조회에 성공하였습니다."),

    // 201 Created - 클라이언트의 요청을 서버가 정상적으로 처리했고 새로운 리소스가 생겼다.,
    CREATED(201, 200, "리소스 생성에 성공하였습니다."), CREATED_COMMENT(201, 201, "댓글 등록에 성공하였습니다."),
    CREATED_POST(201, 202, "게시글 등록에 성공하였습니다."), CREATED_SERIES(201, 203, "시리즈 등록에 성공하였습니다."),

    // 202 Accepted - 클라이언트의 요청은 정상적이나, 서버가 아직 요청을 완료하지 못했다. 비동기
    ACCEPTED(202, 200, "요청에 성공하였습니다."),

    // 204 No Content - 클라이언트의 요청은 정상적이다. 하지만 컨텐츠를 제공하지 않는다.
    NO_CONTENT(204, 200, "요청에 성공하였습니다."), SUCCESS_UPDATE_COMMENT(204, 201, "댓글 수정에 성공하였습니다."),
    SUCCESS_DELETE_COMMENT(204, 202, "댓글 삭제에 성공하였습니다."), SUCCESS_UPDATE_POST(204, 201, "게시글 수정에 성공하였습니다."),
    SUCCESS_DELETE_POST(204, 202, "게시글 삭제에 성공하였습니다."), SUCCESS_UPDATE_SERIES(204, 201, "시리즈 수정에 성공하였습니다."),
    SUCCESS_DELETE_SERIES(204, 202, "시리즈 삭제에 성공하였습니다."),

    /*
     * 4XX Client errors
     */
    // 400 Rad Request - 클라이언트의 요청이 유효하지 않아 더 이상 작업을 진행하지 않는 경우
    BAD_REQUEST(400, 400, "요청에 실패하였습니다."), FAILED_TO_REQUEST(400, 401, "데이터를 불러오는데 실패하였습니다."),
    NO_VALUES(400, 410, "입력되지 않은 값이 존재합니다."), NO_CONTENTS(400, 411, "내용을 입력해주세요."),
    BAD_PASSWORD_VALUE(400, 414, "올바른 비밀번호를 입력해주세요."), BAD_ID_VALUE(400, 416, "올바른 아이디를 입력해주세요."),
    BAD_VALUES(400, 421, "올바르지 않은 값이 존재합니다."),

    // 401 Unauthorized - 클라이언트가 권한이 없기 때문에 작업을 진행할 수 없는 경우
    UNAUTHORIZED(401, 400, "권한이 없습니다."),

    // 403 Forbidden - 클라이언트가 권한이 없기 때문에 작업을 진행할 수 없는 경우
    FORBIDDEN(403, 400, "권한이 없습니다."),

    // 404 Not Found - 클라이언트가 요청한 자원이 존재하지 않다.
    NOT_FOUND(404, 400, "NOT FOUND"), NOT_FOUND_POST(404, 401, "게시글 정보가 존재하지 않습니다."),
    NOT_FOUND_SERIES(404, 402, "시리즈 정보가 존재하지 않습니다."), NOT_FOUND_COMMENT(404, 403, "댓글 정보가 존재하지 않습니다."),

    // 405 Method Not Allowed - 클라이언트의 요청이 허용되지 않는 메소드인 경우
    METHOD_NOT_ALLOWED(405, 400, "허용되지 않는 HTTP Method 입니다."),

    // 409 Conflict - 클라이언트의 요청이 서버의 상태와 충돌이 발생한 경우
    CONFLICT(409, 400, "충돌이 발생하였습니다."),

    // 429 Too Many Requests - 클라이언트가 일정 시간 동안 너무 많은 요청을 보낸 경우
    TOO_MANY_REQUESTS(429, 400, "요청이 너무 많습니다."),

    /*
     * 5XX Server errors
     */
    // 500 내부 서버 오류가 발생한 경우
    SERVER_ERROR(500, 500, "서버와의 통신에 실패하였습니다."), INFOTECH_SERVER_ERROR(500, 501, "서버와의 통신에 실패하였습니다."),
    DATABASE_ERROR(500, 502, "데이터베이스 연결에 실패하였습니다.");

    private final int status;
    private final int code;
    private final String message;
}