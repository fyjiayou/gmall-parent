package com.fystart.gmall.product.controller;

import com.fystart.gmall.common.result.Result;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author fy
 * @date 2022/12/4 13:52
 */
@RestController
@RequestMapping("/admin/product")
@Slf4j
public class FileUploadController {

    //  获取文件上传对应的地址
    @Value("${minio.endpointUrl}")
    public String endpointUrl;

    @Value("${minio.accessKey}")
    public String accessKey;

    @Value("${minio.secreKey}")
    public String secreKey;

    @Value("${minio.bucketName}")
    public String bucketName;

    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception {

        //  准备获取到上传的文件路径！
        String url = "";

        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        // MinioClient minioClient = new MinioClient("https://play.min.io", "Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG");
        MinioClient minioClient = MinioClient.builder()
                        .endpoint(endpointUrl)
                        .credentials(accessKey, secreKey)
                        .build();

        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

        if (isExist) {
            log.info("Bucket {} already exists.",bucketName);
        } else {
            // 创建一个名为 bucketName 的存储桶，用于存储照片的zip文件。
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }

        //  定义一个文件的名称: 文件上传的时候，名称不能重复！
        String fileName = System.currentTimeMillis() + UUID.randomUUID().toString();

        // 使用putObject上传一个文件到存储桶中。
        //  minioClient.putObject("asiatrip","asiaphotos.zip", "/home/user/Photos/asiaphotos.zip");
        minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());

        //  文件上传之后的路径： http://ip:port/gmall/xxxxxx
        url = endpointUrl + "/" + bucketName + "/" + fileName;

        log.info("图片上传url：{}",url);
        //  将文件上传之后的路径返回给页面！

        return Result.ok(url);
    }

}
