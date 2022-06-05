package com.mapto.api.common.file;

import com.mapto.api.app.file.dto.FileDTO;
import com.mapto.api.common.model.type.FileType;
import com.mapto.api.common.util.AmazonS3Util;
import com.mapto.api.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileUploader {
    private final AmazonS3Util amazonS3Util;

    public List<FileDTO.Basic> upload(List<MultipartFile> fileList, String dir) throws IOException {
        List<FileDTO.Basic> result = new ArrayList<>();
        for (MultipartFile multipartFile : fileList) {
            result.add(uploadS3(dir, multipartFile));
        }
        return result;
    }

    public FileDTO.Basic upload(MultipartFile file, String dir) throws IOException {
        return uploadS3(dir, file);
    }

    private FileDTO.Basic uploadS3(String dir, MultipartFile multipartFile) throws IOException {
        String url = amazonS3Util.upload(multipartFile, dir);
        String name = Normalizer.normalize(Objects.requireNonNull(multipartFile.getOriginalFilename()), Normalizer.Form.NFC);
        FileType type = FileUtil.toFileType(FileUtil.getFileType(name));
        FileDTO.Basic fileDTO = new FileDTO.Basic();
        fileDTO.setName(name);
        fileDTO.setUrl(url);
        fileDTO.setType(type);
        return fileDTO;
    }
}
