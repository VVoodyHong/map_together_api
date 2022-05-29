package com.mapto.api.common.file;

import com.mapto.api.app.file.entity.File;
import com.mapto.api.common.model.type.FileType;
import com.mapto.api.common.util.AmazonS3Util;
import com.mapto.api.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileUploader {
    private final AmazonS3Util amazonS3Util;

    public File upload(MultipartFile file, String dir) throws IOException {
        String url = amazonS3Util.upload(file, dir);
        String name = Normalizer.normalize(Objects.requireNonNull(file.getOriginalFilename()), Normalizer.Form.NFC);
        FileType type = FileUtil.toFileType(FileUtil.getFileType(name));
//        String size = FileUtil.convertFileSize(amazonS3Util.getFileSize());
        return File.builder()
                .name(name)
                .url(url)
                .type(type)
                .build();
    }
}
