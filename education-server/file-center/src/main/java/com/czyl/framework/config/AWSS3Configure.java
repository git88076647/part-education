package com.czyl.framework.config;/**
 * Created by 44962 on 2020/7/21.
 */

import com.czyl.framework.properties.FileServerProperties;
import com.czyl.project.system.domain.SysFile;
import com.czyl.project.system.service.impl.AbstractFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  AWSS3Configure
 * @auther 66
 * @date 2020/7/21 9:31
 **/

@Configuration
@ConditionalOnProperty(name = "czyl.filesystem.type", havingValue = "aws")
public class AWSS3Configure {

    @Autowired
    private FileServerProperties fileProperties;

    private static S3Client s3client;

    @Service
    public class Awss3OssServiceImpl extends AbstractFileService {


        @Override
        protected String fileType() {
            return fileProperties.getType();
        }

        @Override
        protected void uploadFile(MultipartFile file, SysFile sysFile) throws Exception {

            String fileName = file.getOriginalFilename();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileKey =sdf.format(date);
            StringBuilder sb = new StringBuilder();
            sb.append("/kelun/masterdata/");
            sb.append(fileKey);
            sb.append("/");
            sb.append(System.currentTimeMillis());
            String AWSkey = sb.toString();
            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                    fileProperties.getAwss3s().getAccessKey(),
                    fileProperties.getAwss3s().getAccessKeySecret());
            s3client = S3Client.builder().region(Region.of(String.valueOf(Region.CN_NORTHWEST_1))).
                    credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
            PutObjectResponse response = s3client.putObject(PutObjectRequest.builder()
                    .bucket(fileProperties.getAwss3s().getBucketName()).key(AWSkey).build(), RequestBody.fromBytes(file.getBytes()));
            sysFile.setPath(AWSkey);
            sysFile.setName(fileName);
            sysFile.setSrcType("aws");
            sysFile.setUrl("/common/download/"+sysFile.getFileId());
        }

        @Override
        protected boolean deleteFile(SysFile sysFile) {

            return true;
        }


        @Override
        public String getAbsolutePath(SysFile sysfile) {

            ResponseInputStream<GetObjectResponse> responseInputStream = null;
            AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                    fileProperties.getAwss3s().getAccessKey(),
                    fileProperties.getAwss3s().getAccessKeySecret());
            try {
                s3client = S3Client.builder().region(Region.of(String.valueOf(Region.CN_NORTHWEST_1))).
                        credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
                responseInputStream = s3client.getObject(GetObjectRequest.builder().bucket(fileProperties.getAwss3s().getBucketName()).key(sysfile.getPath()).build());

                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                //创建一个Buffer字符串
                byte[] buffer = new byte[1024];
                //每次读取的字符串长度，如果为-1，代表全部读取完毕
                int len = 0;
                //使用一个输入流从buffer里把数据读取出来
                while( (len=responseInputStream.read(buffer)) != -1 ){
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                    outStream.write(buffer, 0, len);
                }
                //关闭输入流
                responseInputStream.close();
                //把outStream里的数据写入内存

                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = outStream.toByteArray();
                //new一个文件对象用来保存图片，默认保存当前工程根目录
                File imageFile = new File(fileProperties.getAwss3s().getRootPath()+sysfile.getName());
                //创建输出流
                FileOutputStream fileOutStream = new FileOutputStream(imageFile);
                //写入数据
                fileOutStream .write(data);
            } catch (Exception e) {

            }
            return fileProperties.getAwss3s().getRootPath()+sysfile.getName();
        }
    }
}
