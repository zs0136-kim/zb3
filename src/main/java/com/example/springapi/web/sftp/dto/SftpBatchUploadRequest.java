package com.example.springapi.web.sftp.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

/**
 * SFTP バッチアップロード用 DTO。
 */
public record SftpBatchUploadRequest(
        @NotEmpty(message = "uploads must not be empty")
        List<@Valid SftpUploadRequest> uploads
) {
}

