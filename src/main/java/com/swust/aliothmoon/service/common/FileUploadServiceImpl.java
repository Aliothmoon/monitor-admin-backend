package com.swust.aliothmoon.service.common;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.swust.aliothmoon.service.common.iface.FileUploadService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService, InitializingBean {
    @Value("${fileserver.root}")
    private String root;
    @Value("${fileserver.prefix}")
    private String prefix;


    @SneakyThrows
    @Override
    public boolean upload(String key, MultipartFile file) {
        File target = new File(root, key);
        File parent = target.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try (InputStream ins = file.getInputStream()) {
            try (FileOutputStream fos = new FileOutputStream(target)) {
                IoUtil.copy(ins, fos);
            }
        }
        return true;
    }

    @SneakyThrows
    @Override
    public boolean upload(String key, File origin) {
        File target = new File(root, key);
        File parent = target.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        try (FileChannel channel = FileChannel.open(origin.toPath(), StandardOpenOption.READ)) {
            try (FileOutputStream fos = new FileOutputStream(target)) {
                channel.transferTo(0, origin.length(), fos.getChannel());
            }
        }
        return true;
    }


    @Override
    public String getFileUrl(String key) {
        String val = StrUtil.replace(key, "\\", "/");
        return prefix + val;
    }


    @Override
    public void afterPropertiesSet() {
        log.info("Storage Root {} URL Prefix {}", root, prefix);
    }
}
