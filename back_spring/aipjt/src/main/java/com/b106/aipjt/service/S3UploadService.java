package com.b106.aipjt.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public String upload(MultipartFile file) throws IOException {

        //추가 부분
        ObjectMetadata objMeta = new ObjectMetadata();
        byte[] bytes = IOUtils.toByteArray(file.getInputStream());
        objMeta.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);
        String fileName = "images/"+file.getOriginalFilename();
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, byteArrayIs, objMeta)
            .withCannedAcl(CannedAccessControlList.PublicRead));


        // 원래 부분
//        String fileName = "images/"+file.getOriginalFilename();
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
//            .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // s3에서 audioUrl 받기
    public String getAudioUrl(String audioName) {
        return amazonS3Client.getUrl(bucket, audioName).toString();
    }
}
