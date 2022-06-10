package com.mapto.api.common.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

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

    public boolean delete(String path) throws UnsupportedEncodingException {
        String[] array = path.split("/");
        List<String> list = new ArrayList<>(Arrays.asList(array).subList(3, array.length));
        StringBuilder key = new StringBuilder();
        for(int i = 0; i < list.size(); i++) {
            key.append(list.get(i));
            if(i != list.size() -1) {
                key.append("/");
            }
        }
        return deleteS3(URLDecoder.decode(String.valueOf(key), "UTF-8"));
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
        try{
            amazonS3.putObject(new PutObjectRequest(bucketName, path, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
            if(removeTempFile(uploadFile)) {
                return amazonS3.getUrl(bucketName, path).toString();
            } else {
                return null;
            }
        } catch (AmazonServiceException e) {
            log.error("deleteS3 error::" + e);
            return null;
        }
    }

    private boolean deleteS3(String key) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
            return true;
        } catch (AmazonServiceException e) {
            log.error("deleteS3 error::" + e);
            return false;
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
