package com.example.springapi.application.sftp;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import com.example.springapi.common.sftp.SftpClient;
import com.example.springapi.common.sftp.SftpClientException;
import com.example.springapi.web.sftp.dto.SftpBatchUploadRequest;
import com.example.springapi.web.sftp.dto.SftpDownloadRequest;
import com.example.springapi.web.sftp.dto.SftpDownloadResponse;
import com.example.springapi.web.sftp.dto.SftpListResponse;
import com.example.springapi.web.sftp.dto.SftpUploadRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * SFTP 操作をまとめたサービス。
 */
@Service
public class SftpService {

    private static final Logger log = LoggerFactory.getLogger(SftpService.class);

    /**
     * SFTPクライアント
     */
    private final SftpClient sftpClient;

    /**
     * SFTPサービスのコンストラクタ
     * @param sftpClient
     */
    public SftpService(SftpClient sftpClient) {
        this.sftpClient = sftpClient;
    }

    /**
     * ファイルをアップロード
     * @param request
     */
    public void upload(SftpUploadRequest request) {
        byte[] fileBytes = decodeBase64(request.base64Content());
        String remotePath = buildRemotePath(request.remoteDirectory(), request.fileName());
        log.info("Uploading file to {}", remotePath);
        sftpClient.upload(new ByteArrayInputStream(fileBytes), remotePath);
    }

    /**
     * ファイルを複数アップロード
     * @param request
     */
    public void uploadBatch(SftpBatchUploadRequest request) {
        request.uploads().forEach(this::upload);
    }

    /**
     * ファイルをダウンロード
     * @param request
     * @return
     */
    public SftpDownloadResponse download(SftpDownloadRequest request) {
        log.info("Downloading file {}", request.remotePath());
        byte[] fileBytes = sftpClient.download(request.remotePath());
        String encoded = Base64.getEncoder().encodeToString(fileBytes);
        String fileName = extractFileName(request.remotePath());
        return new SftpDownloadResponse(fileName, encoded);
    }

    /**
     * ディレクトリをリストアップ
     * @param directory
     * @return
     */
    public SftpListResponse list(String directory) {
        List<String> entries = sftpClient.list(directory);
        return new SftpListResponse(directory, entries);
    }

    /**
     * ファイルを削除
     * @param remotePath
     */
    public void delete(String remotePath) {
        log.info("Deleting file {}", remotePath);
        sftpClient.delete(remotePath);
    }

    /**
     * Base64をデコード
     * @param base64
     * @return
     */
    private byte[] decodeBase64(String base64) {
        try {
            return Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalArgumentException ex) {
            throw new SftpClientException("Invalid base64 content", ex);
        }
    }

    /**
     * リモートパスを構築
     * @param directory
     * @param fileName
     * @return
     */
    private String buildRemotePath(String directory, String fileName) {
        if (!StringUtils.hasText(directory) || !StringUtils.hasText(fileName)) {
            throw new SftpClientException("Directory and fileName must be provided");
        }
        String normalizedDir = directory.endsWith("/") ? directory.substring(0, directory.length() - 1) : directory;
        String normalizedFile = fileName.startsWith("/") ? fileName.substring(1) : fileName;
        return normalizedDir + "/" + normalizedFile;
    }

    /**
     * リモートパスからファイル名を抽出
     * @param remotePath
     * @return
     */
    private String extractFileName(String remotePath) {
        if (!StringUtils.hasText(remotePath)) {
            return "";
        }
        int idx = remotePath.lastIndexOf('/');
        return idx >= 0 ? remotePath.substring(idx + 1) : remotePath;
    }
}

