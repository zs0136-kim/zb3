package com.example.springapi.web.sftp.dto;

/**
 * SFTP ダウンロードレスポンス。
 */
public record SftpDownloadResponse(
        String fileName,
        String base64Content
) {
}

