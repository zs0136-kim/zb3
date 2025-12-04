package com.example.springapi.web.sftp;

import com.example.springapi.application.sftp.SftpService;
import com.example.springapi.common.api.ApiResponse;
import com.example.springapi.web.sftp.dto.SftpBatchUploadRequest;
import com.example.springapi.web.sftp.dto.SftpDownloadRequest;
import com.example.springapi.web.sftp.dto.SftpDownloadResponse;
import com.example.springapi.web.sftp.dto.SftpListResponse;
import com.example.springapi.web.sftp.dto.SftpUploadRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SFTP 操作用 REST コントローラー。
 */
@Validated
@RestController
@RequestMapping("/api/sftp")
public class SftpController {

    private final SftpService sftpService;

    public SftpController(SftpService sftpService) {
        this.sftpService = sftpService;
    }

    @PostMapping("/upload")
    public ApiResponse<Void> upload(@Valid @RequestBody SftpUploadRequest request) {
        sftpService.upload(request);
        return ApiResponse.ok(null);
    }

    @PostMapping("/upload/batch")
    public ApiResponse<Void> uploadBatch(@Valid @RequestBody SftpBatchUploadRequest request) {
        sftpService.uploadBatch(request);
        return ApiResponse.ok(null);
    }

    @PostMapping("/download")
    public ApiResponse<SftpDownloadResponse> download(@Valid @RequestBody SftpDownloadRequest request) {
        return ApiResponse.ok(sftpService.download(request));
    }

    @GetMapping("/list")
    public ApiResponse<SftpListResponse> list(@RequestParam @NotBlank String directory) {
        return ApiResponse.ok(sftpService.list(directory));
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void> delete(@RequestParam @NotBlank String remotePath) {
        sftpService.delete(remotePath);
        return ApiResponse.ok(null);
    }
}

