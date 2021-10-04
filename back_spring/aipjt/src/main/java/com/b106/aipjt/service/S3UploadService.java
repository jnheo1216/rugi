package com.b106.aipjt.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile file) throws IOException {
        String fileName = "images/"+file.getOriginalFilename();

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
            .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // s3에서 audioUrl 받기
    public String getAudioUrl() {
        return amazonS3Client.getUrl(bucket, "audio/sample.mp3").toString();
    }
}
