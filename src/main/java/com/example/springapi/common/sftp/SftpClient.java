package com.example.springapi.common.sftp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * JSch を利用した SFTP クライアント。
 */
@Component
public class SftpClient {

    private static final Logger log = LoggerFactory.getLogger(SftpClient.class);

    private final SftpProperties properties;
    private final JSch jsch;
    private volatile boolean identityAdded = false;

    public SftpClient(SftpProperties properties, JSch jsch) {
        this.properties = properties;
        this.jsch = jsch;
    }

    public void upload(InputStream inputStream, String remotePath) {
        execute(channel -> {
            try {
                channel.put(inputStream, resolvePath(remotePath));
            } catch (com.jcraft.jsch.SftpException ex) {
                throw new SftpClientException("Failed to upload file: " + remotePath, ex);
            }
            return null;
        });
    }

    public byte[] download(String remotePath) {
        return execute(channel -> {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                channel.get(resolvePath(remotePath), outputStream);
                return outputStream.toByteArray();
            } catch (com.jcraft.jsch.SftpException | IOException ex) {
                throw new SftpClientException("Failed to download file: " + remotePath, ex);
            }
        });
    }

    public List<String> list(String directory) {
        return execute(channel -> {
            try {
                @SuppressWarnings("unchecked")
                List<ChannelSftp.LsEntry> entries = channel.ls(resolvePath(directory));
                return entries.stream()
                        .map(ChannelSftp.LsEntry::getFilename)
                        .filter(filename -> !".".equals(filename) && !"..".equals(filename))
                        .collect(Collectors.toList());
            } catch (com.jcraft.jsch.SftpException ex) {
                throw new SftpClientException("Failed to list directory: " + directory, ex);
            }
        });
    }

    public void delete(String remotePath) {
        execute(channel -> {
            try {
                channel.rm(resolvePath(remotePath));
            } catch (com.jcraft.jsch.SftpException ex) {
                throw new SftpClientException("Failed to delete file: " + remotePath, ex);
            }
            return null;
        });
    }

    private <T> T execute(SftpOperation<T> operation) {
        Session session = null;
        ChannelSftp channelSftp = null;
        try {
            session = createSession();
            Channel channel = session.openChannel("sftp");
            channel.connect(properties.getConnectTimeout());
            channelSftp = (ChannelSftp) channel;
            return operation.apply(channelSftp);
        } catch (JSchException ex) {
            throw new SftpClientException("Failed to open SFTP session", ex);
        } catch (Exception ex) {
            throw new SftpClientException("SFTP operation failed", ex);
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    private Session createSession() throws JSchException {
        addIdentityIfNeeded();
        Session session = jsch.getSession(properties.getUsername(), properties.getHost(), properties.getPort());
        if (StringUtils.hasText(properties.getPassword())) {
            session.setPassword(properties.getPassword());
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(properties.getConnectTimeout());
        if (properties.getSocketTimeout() > 0) {
            session.setTimeout(properties.getSocketTimeout());
        }
        log.debug("Connected to SFTP server {}:{}", properties.getHost(), properties.getPort());
        return session;
    }

    private void addIdentityIfNeeded() throws JSchException {
        if (!StringUtils.hasText(properties.getPrivateKeyPath()) || identityAdded) {
            return;
        }
        if (StringUtils.hasText(properties.getPassphrase())) {
            jsch.addIdentity(properties.getPrivateKeyPath(), properties.getPassphrase());
        } else {
            jsch.addIdentity(properties.getPrivateKeyPath());
        }
        identityAdded = true;
    }

    private String resolvePath(String remotePath) {
        if (!StringUtils.hasText(remotePath)) {
            throw new SftpClientException("Remote path must not be blank");
        }
        if (remotePath.contains("..")) {
            throw new SftpClientException("Path traversal is not allowed: " + remotePath);
        }
        String base = properties.getBaseDirectory();
        if (!StringUtils.hasText(base) || "/".equals(base)) {
            return remotePath;
        }
        String normalizedBase = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
        String normalizedPath = remotePath.startsWith("/") ? remotePath : "/" + remotePath;
        return normalizedBase + normalizedPath;
    }

    @FunctionalInterface
    private interface SftpOperation<T> {
        T apply(ChannelSftp channel) throws Exception;
    }
}

