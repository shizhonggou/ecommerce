package com.isechome.ecommerce.util;


import java.io.File;
import java.io.IOException;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    public static String executeUpload(MultipartFile file) throws Exception{
        
        String path=System.getProperty("user.dir")+"/src/main/resources/static";
        String path1="/upload/"+getNowDate()+new Random().nextInt(9999)+"/";
        String uploadDir=path+path1;
        File dir = new File(uploadDir);
        if(!dir.exists()){
            System.out.println("路径是否存在");
            dir.mkdirs();
        }
        //文件后缀
       // String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //文件随机名
        String filename =file.getOriginalFilename();
        System.out.println("文件名称==="+filename);
        //创建文件对象
        File serverFile = new File(uploadDir + filename);
        //转储文件
        file.transferTo(serverFile);
        return ".."+path1+filename;
    }


    public static File transferToFile(MultipartFile multipartFile) {
        File file = null;
        try {   
           String originalFilename = multipartFile.getOriginalFilename();
           String[] filename = originalFilename.split(".");
           file=File.createTempFile(filename[0], filename[1]);
           multipartFile.transferTo(file);
            file.deleteOnExit();        
       } catch (IOException e) {
           e.printStackTrace();
       }
       return file;
   }

    /**
     * 获取现在时间
     *
     * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
       
        return dateString;
    }
       
}
