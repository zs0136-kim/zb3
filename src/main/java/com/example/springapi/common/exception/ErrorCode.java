package com.example.springapi.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND("COMMON_404", "対象データが存在しません。"),
    VALIDATION_ERROR("COMMON_400", "入力内容に誤りがあります。"),
    BUSINESS_ERROR("COMMON_409", "ビジネスロジックエラーが発生しました。"),
    INTERNAL_ERROR("COMMON_500", "システムエラーが発生しました。");

    private final String code;
    private final String defaultMessage;
}
