package com.example.springapi.web.sftp.dto;

import java.util.List;

/**
 * SFTP ディレクトリ一覧レスポンス。
 */
public record SftpListResponse(
        String directory,
        List<String> entries
) {
}

