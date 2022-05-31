package com.mapto.api.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Util {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.location}")
    private String location;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private String path;
    private String fileName;

    public String upload(MultipartFile multipartFile, String dir) throws IOException {
        fileName = multipartFile.getOriginalFilename();
        path = location + dir + "/" + generateFileName();
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 컨버팅 실패"));
        return putS3(uploadFile);
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(Objects.requireNonNull(fileName));
        if(convertFile.createNewFile()) {
            try(FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private String generateFileName() {
        Long currentTimeMillis = System.currentTimeMillis();
        String encryptedValue = AES256Util.encrypt(String.valueOf(currentTimeMillis)).replaceAll("/", "");
        String fileType = FileUtil.getFileType(fileName);
        return encryptedValue + "." + fileType;
    }

    private String putS3(File uploadFile) {
        amazonS3.putObject(new PutObjectRequest(bucketName, path, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        if(removeTempFile(uploadFile)) {
            return amazonS3.getUrl(bucketName, path).toString();
        } else {
            return null;
        }
    }

    private boolean removeTempFile(File tempFile) {
        if(tempFile.delete()) {
            return true;
        } else {
            log.error("임시 파일 삭제에 실패했습니다. 파일 이름:: {}", tempFile.getName());
            return false;
        }
    }

    public long getFileSize() {
        if (path == null) {
            throw new IllegalArgumentException("업로드 후 파일 크기를 알 수 있습니다.");
        }
        return amazonS3.getObjectMetadata(bucketName, path).getContentLength();
    }
}
