package com.ssafy.ssafying.model.service;

import java.io.File;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;


@Slf4j
@Service
@NoArgsConstructor
public class S3Service {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(String image, String dirName) throws Exception{
        File uploadFile = createNewFile(image,dirName);
        String uploadImageUrl = putS3(uploadFile, dirName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    private File createNewFile(String image,String dirName) throws Exception{
        String url = System.getProperty("user.dir") + dirName + ".png";

        File uploadFile = new File(url);
        String jsonData = image;
        byte[] BinaryData = DatatypeConverter.parseBase64Binary(jsonData.substring(jsonData.indexOf(",") + 1));
        ImageIO.write(ImageIO.read(new ByteArrayInputStream(BinaryData)), "png", uploadFile);
        
        return uploadFile;
    }

    private String putS3(File uploadFile, String dirName) {
        s3Client.putObject(new PutObjectRequest(bucket, dirName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, dirName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }
}