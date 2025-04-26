package org.sopt.post.controller;

public enum ResponseMessage {

    POST_CREATE_SUCCESS("게시글 생성에 성공했습니다."),
    POST_GET_ALL_SUCCESS("전체 게시글 조회에 성공했습니다."),
    POST_UPDATE_SUCCESS("게시글 수정에 성공했습니다."),
    POST_GET_DETAIL_SUCCESS("특정 게시글 조회에 성공했습니다."),
    POST_DELETE_SUCCESS("게시글 삭제에 성공했습니다."),
    POST_SEARCH_SUCCESS("게시글 검색에 성공했습니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
