package com.example.springapi.web.sftp.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * SFTP ファイルダウンロード用 DTO。
 */
public record SftpDownloadRequest(
        @NotBlank(message = "remotePath must not be blank")
        String remotePath
) {
}

