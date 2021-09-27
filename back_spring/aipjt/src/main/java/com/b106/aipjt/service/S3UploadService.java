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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;
    public static final String CLOUD_FRONT_DOMAIN_NAME = "https://d2dwk4pyx06xno.cloudfront.net";

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    //    S3에 직접 접근하는 것이 아닌, CloudFront을 통해 캐싱된 이미지를 가져올 것
    public String upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
            .withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName;
        // return은 questionService에서 파일경로를 DB에 저장하기 위한 값으로 사용되는데, 사실은 fileName 변수가 S3 객체를 식별하는 key 값이고 이를 DB에 저장
    }

}
