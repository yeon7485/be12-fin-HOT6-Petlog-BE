package com.hot6.backend.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    /* 200 : 요청 성공 */
    SUCCESS(true, 200, "요청에 성공했습니다."),


    /* 1000 ~ 1999 : 회원 관련 에러 */
    //-- 1000 : 회원가입 에러
    USER_SIGNUP_FAILED(false, 1000, "회원가입에 실패했습니다."),
    USER_ALREADY_EXISTS(false, 1001, "이미 존재하는 사용자입니다."),
    //-- 1100 : 로그인 에러
    LOGIN_FAILED(false, 1100, "로그인에 실패했습니다."),
    INVALID_CREDENTIALS(false, 1101, "아이디 또는 비밀번호가 올바르지 않습니다."),
    EMAIL_VERIFY_FAIL(false, 1102, "이메일 인증이 완료되지 않았습니다."),
    //-- 1200 : 계정 정보 수정
    ACCOUNT_UPDATE_FAILED(false, 1200, "계정 정보 수정에 실패했습니다."),
    INVALID_USER_INFO(false, 1201, "잘못된 사용자 정보입니다."),
    //-- 1300 : 유저 정보 조회
    USER_NOT_FOUND(false, 1300, "찾을 수 없는 사용자입니다."),

    /* 2000 ~ 2999 : 채팅 관련 에러 */

    //-- 2000 : 채팅방 접근 / 참여 관련
    CHAT_ROOM_NOT_FOUND(false, 2000, "존재하지 않는 채팅방입니다."),
    CHAT_ROOM_ACCESS_DENIED(false, 2001, "해당 채팅방에 접근할 수 없습니다."),
    CHAT_ROOM_PARTICIPANT_NOT_FOUND(false, 2002, "채팅방 참여자가 아닙니다."),
    CHAT_ROOM_ALREADY_PARTICIPATED(false, 2003, "이미 참여 중인 채팅방입니다."),
    CHAT_ROOM_JOIN_FAILED(false, 2004, "채팅방 참여에 실패했습니다."),
    CHAT_ROOM_LEAVE_FAILED(false, 2005, "채팅방 나가기에 실패했습니다."),

    //-- 2100 : 채팅방 생성/수정 관련
    CHAT_ROOM_CREATION_FAILED(false, 2100, "채팅방 생성에 실패했습니다."),
    CHAT_ROOM_UPDATE_FAILED(false, 2101, "채팅방 수정에 실패했습니다."),
    CHAT_ROOM_DELETE_FAILED(false, 2102, "채팅방 삭제에 실패했습니다."),
    CHAT_ROOM_HASHTAG_FORMAT_INVALID(false, 2103, "해시태그 형식이 잘못되었습니다."),

    //-- 2200 : 채팅 메시지 관련
    CHAT_MESSAGE_SEND_FAILED(false, 2200, "채팅 메시지 전송에 실패했습니다."),
    CHAT_MESSAGE_NOT_FOUND(false, 2201, "존재하지 않는 채팅 메시지입니다."),
    CHAT_MESSAGE_ACCESS_DENIED(false, 2202, "해당 메시지에 접근할 수 없습니다."),
    CHAT_MESSAGE_READ_UPDATE_FAILED(false, 2203, "메시지 읽음 처리에 실패했습니다."),

    //-- 2300 : WebSocket 관련
    CHAT_WEBSOCKET_CONNECT_FAILED(false, 2300, "웹소켓 연결에 실패했습니다."),
    CHAT_WEBSOCKET_UNAUTHORIZED(false, 2301, "웹소켓 인증에 실패했습니다."),
    CHAT_WEBSOCKET_INVALID_HEADER(false, 2302, "웹소켓 헤더 정보가 유효하지 않습니다."),

    //-- 2400 : 기타 채팅 도메인 에러
    CHAT_UNEXPECTED_ERROR(false, 2400, "알 수 없는 채팅 오류가 발생했습니다."),
    CHAT_FEATURE_NOT_IMPLEMENTED(false, 2401, "아직 구현되지 않은 채팅 기능입니다."),

    /* 3000~ 3999 : 일정 관련 에러 */
    //-- 3000 : 일정 조회
    SCHEDULE_NOT_FOUND(false, 3000, "일정 조회에 실패했습니다."),
    //-- 3100 : 일정 등록
    SCHEDULE_CREATE_FAILED(false, 3100, "일정 등록에 실패했습니다."),
    //-- 3200 : 일정 수정
    SCHEDULE_UPDATE_FAILED(false, 3200, "일정 수정에 실패했습니다."),


    /* 4000 ~ 4999 : 주문 관련 에러 */
    //-- 4000 : 주문 실패
    ORDER_FAILED(false, 4000, "주문에 실패했습니다."),
    //-- 4100 : 주문내역 조회 에러
    ORDER_HISTORY_LOOKUP_FAILED(false, 4100, "주문 내역 조회에 실패했습니다."),
    //-- 4200 : 주문 취소 에러
    ORDER_CANCEL_FAILED(false, 4200, "주문 취소에 실패했습니다."),


    /* 5000 ~ 5999 : 결제 관련 에러 */
    //-- 5000 : 결제 요청 에러
    PAYMENT_REQUEST_FAILED(false, 5000, "결제 요청에 실패했습니다."),
    //-- 5100 : 결제 내역 조회 에러
    PAYMENT_HISTORY_LOOKUP_FAILED(false, 5100, "결제 내역 조회에 실패했습니다."),
    //-- 5200 : 결제 취소 에러
    PAYMENT_CANCEL_FAILED(false, 5200, "결제 취소에 실패했습니다."),


    /* 6000 ~ 6999 : 리뷰 관련 에러 */
    //-- 6000 : 상품 리뷰 등록 에러
    PRODUCT_REVIEW_REGIST_FAILED(false, 6000, "상품 리뷰 등록에 실패했습니다."),
    //-- 6100 : 상품 리뷰 조회 에러
    PRODUCT_REVIEW_LOOKUP_FAILED(false, 6100, "상품 리뷰 조회에 실패했습니다."),
    //-- 6200 : 상품 리뷰 변경 삭제 에러
    PRODUCT_REVIEW_UPDATE_FAILED(false, 6200, "상품 리뷰 수정/삭제에 실패했습니다."),
    //-- 6300 : 식당 리뷰 등록 에러
    STORE_REVIEW_REGIST_FAILED(false, 6300, "식당 리뷰 등록에 실패했습니다."),
    //-- 6400 : 식당 리뷰 조회 에러
    STORE_REVIEW_LOOKUP_FAILED(false, 6400, "식당 리뷰 조회에 실패했습니다."),
    //-- 6500 : 식당 리뷰 변경 삭제 에러
    STORE_REVIEW_UPDATE_FAILED(false, 6500, "식당 리뷰 수정/삭제에 실패했습니다."),


    /* 7000 ~ 7999 : 예약 관련 에러 */
    //-- 7000 : 예약 요청 에러
    RESERVATION_REQUEST_FAILED(false, 7000, "예약 요청에 실패했습니다."),
    //-- 7100 : 예약 내역 조회 에러
    RESERVATION_HISTORY_LOOKUP_FAILED(false, 7100, "예약 내역 조회에 실패했습니다."),
    //-- 7200 : 예약 변경 취소 에러
    RESERVATION_UPDATE_FAILED(false, 7200, "예약 변경/취소에 실패했습니다."),


    /* 8000 ~ 8999 : 관리자 관련 에러 */
    ADMIN_CATEGORY_DELETE_FAILED_REASON_CONNECT_STORE(false, 8000, "삭제하려는 카테고리는 Store에 하나 이상 연결되어 있는 카테고리 입니다. Store에서 카테고리 수정을 먼저 한 후 삭제해주세요."),


    /* 9000 ~ 9999 : 카테고리 관련 에러 */
    CATEGORY_NOT_FOUND(false, 9000, "카테고리를 찾을 수 없습니다."),
    //-- 9100 : 카테고리 등록 에러
    CATEGORY_REGIST_FAILED(false, 9100, "카테고리 등록에 실패했습니다."),
    //-- 9200 : 부모 카테고리 조회 에러
    CATEGORY_NOT_FOUND_PARENT_CATEGORY(false, 9200, "부모 카테고리를 찾을 수 없습니다.");



    private final boolean isSuccess;
    private final int code;
    private final String message;
}
