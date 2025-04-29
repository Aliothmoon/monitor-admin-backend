package com.swust.aliothmoon.service.common.iface;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUploadService {

    boolean upload(String key, MultipartFile file);

    boolean upload(String key, File file);

    String getFileUrl(String key);
}
