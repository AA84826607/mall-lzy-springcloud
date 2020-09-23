package com.lzy.springcloud.manage.utils.Utils;


import org.csource.common.IniFileReader;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PmsUploadUtil {
    public static String uploadImage(MultipartFile multipartFile){

        String imgUrl="http://192.168.2.166";
        // 上传图片到服务器
        // 配置fdfs的全局链接地址
        Properties properties = new Properties();
        InputStream in = IniFileReader.loadFromOsFileSystemOrClasspathAsStream("tracker.properties");


        try {
            properties.load(in);
            ClientGlobal.initByProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        TrackerClient trackerClient = new TrackerClient();

        // 获得一个trackerServer的实例
        TrackerServer trackerServer = null;
        try {
            trackerServer = trackerClient.getTrackerServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 通过tracker获得一个Storage链接客户端
        StorageClient storageClient = new StorageClient(trackerServer,null);

        try {

            byte[] bytes = multipartFile.getBytes();// 获得上传的二进制对象

            // 获得文件后缀名
            String originalFilename = multipartFile.getOriginalFilename();// a.jpg
            System.out.println(originalFilename);
            int i = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(i+1);

            String[] uploadInfos = storageClient.upload_file(bytes, extName, null);

            for (String uploadInfo : uploadInfos) {
                imgUrl += "/"+uploadInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgUrl;

    }

}
