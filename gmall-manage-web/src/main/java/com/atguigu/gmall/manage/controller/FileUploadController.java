package com.atguigu.gmall.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
//http://localhost:8082/fileUpload
public class FileUploadController {
    @Value("${fileServer.url}")
    private String fileUrl;

    @RequestMapping("fileUpload")
    public String fileUpload(MultipartFile file) throws IOException, MyException {
        String imgUrl = fileUrl;

        if (file != null) {
            //上传谁回显谁
            //表示读取配置文件中的tracker.conf
            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            //初始化
            ClientGlobal.init(configFile);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //存储数据
            StorageClient storageClient = new StorageClient(trackerServer, null);
            //获取上传文件的名称
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀名
            String exName = StringUtils.substringAfterLast(originalFilename, ".");

            //保存数据
            String[] upload_file = storageClient.upload_file(file.getBytes(), exName, null);
            for (int i = 0; i < upload_file.length; i++) {
                String path = upload_file[i];
                //System.out.println("s="+s);
                imgUrl += "/" + path;   //imgUrl=imgUrl+path
                // http://192.168.67.225/group1/M00/00/00/wKhD4V3rSbWAevpcAACGx2c4tJ4978.jpg
                /*
                s = group1
                s = M00/00/00/wKhD4V3rSbWAevpcAACGx2c4tJ4978.jpg
                */
            }

        }
        return imgUrl;
    }
}
