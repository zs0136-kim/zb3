package com.example.springapi.web.sftp.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * SFTP ファイルアップロード用 DTO。
 */
public record SftpUploadRequest(
        @NotBlank(message = "remoteDirectory must not be blank")
        String remoteDirectory,
        @NotBlank(message = "fileName must not be blank")
        String fileName,
        @NotBlank(message = "content must not be blank")
        String base64Content
) {
}

