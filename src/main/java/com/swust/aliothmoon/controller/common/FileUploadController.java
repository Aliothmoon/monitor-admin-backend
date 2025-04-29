package com.swust.aliothmoon.controller.common;

import com.swust.aliothmoon.define.R;
import com.swust.aliothmoon.service.common.iface.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/common")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService service;

    @PostMapping("/upload")
    public R<Boolean> upload(
            @RequestPart("file") MultipartFile file,
            @RequestPart("filename") String filename
    ) {
        return R.ok(service.upload(filename, file));
    }

}
