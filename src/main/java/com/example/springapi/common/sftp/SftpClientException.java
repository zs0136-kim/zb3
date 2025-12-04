package com.example.springapi.common.sftp;

/**
 * SFTP 操作で発生した例外。
 */
public class SftpClientException extends RuntimeException {

    public SftpClientException(String message) {
        super(message);
    }

    public SftpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

