package com.example.springapi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND("COMMON_404", "対象データが存在しません。"),
    VALIDATION_ERROR("COMMON_400", "入力内容に誤りがあります。"),
    BUSINESS_ERROR("COMMON_409", "ビジネスロジックエラーが発生しました。"),
    INTERNAL_ERROR("COMMON_500", "システムエラーが発生しました。"),
    UNAUTHORIZED("AUTH_401", "認証に失敗しました。"),
    INVALID_TOKEN("AUTH_4011", "トークンが不正です。"),
    EXPIRED_TOKEN("AUTH_4012", "トークンの有効期限が切れています。"),
    SFTP_OPERATION_FAILED("SFTP_400", "SFTP 操作に失敗しました。");

    private final String code;
    private final String defaultMessage;
}
