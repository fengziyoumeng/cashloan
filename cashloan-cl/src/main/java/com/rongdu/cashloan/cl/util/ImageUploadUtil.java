package com.rongdu.cashloan.cl.util;

import com.rongdu.cashloan.core.aliyun.AliYunUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

public class ImageUploadUtil {

    /**
     * 图片临时保存在服务器上
     * @param folderName 目录名,例如写:"image"
     * @param realPath 更目录绝对路径
     * @param image
     * @return 返回存放的路径
     */
    public static String tempSave(String realPath, String folderName, MultipartFile image,String filename) {
        OutputStream os = null;
        InputStream imgInputStream = null;
        String imgTempPath="";
        String extension = "";
        try {
            imgInputStream = image.getInputStream();
//            String imgFileName =  image.getOriginalFilename(); //无法获取到拓展名
            //重新生成文件名
            String contentType = image.getContentType();
            System.out.println(">>>>>>>>>>>>>"+contentType);
            if(StringUtil.isEmpty(filename)){
                extension = ".png";
            }else{
                extension = filename.substring(filename.lastIndexOf("."));
            }
            String randomName = UUID.randomUUID().toString();
            String newImgName = randomName+extension;
            imgTempPath = realPath + folderName + File.separatorChar + newImgName;
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(realPath + folderName);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + newImgName);
            // 开始读取
            while ((len = imgInputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                imgInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgTempPath;
    }

    /**
     * 把临时目录中的图片上传到OSS并删除本地图片
     * @param imgTempPath 临时图片路径
     * @param dirName oss目录
     * @return 保存到oss中的url路径
     */
    public static String  uploadOSSDeleteTemp(String imgTempPath,String dirName,String ...oldPath) throws Exception{
        String imgOSSPath = "";
        try {
            if (StringUtil.isNotEmpty(imgTempPath)) {
                File file = new File(imgTempPath);
                //判断服务器上是否已经有图片，有则认为是重新上传，无则认为没有修改图片，不再上传oss
                if (file.exists()) {
                    String imageName = imgTempPath.substring(imgTempPath.lastIndexOf(File.separator)+1);
                    //如果有oldPath，则认为是更新，则直接取oldPath的key作为新文件的key覆盖原来的文件
                    if(oldPath !=null && oldPath.length>0 && StringUtil.isNotBlank(oldPath[0])){
                        imageName = oldPath[0].substring(oldPath[0].indexOf(dirName), oldPath[0].indexOf("?"));
                        imageName = imageName.replaceAll(dirName+"/","");
                    }
                    imgOSSPath = AliYunUtil.uploadH5File(dirName, imageName, file);
                }
                //上传成功后删除本地文件
                File file1 = new File(imgTempPath);
                if (!FileUtil.deleteFile(file1)) {
                    throw new RuntimeException("临时图片删除失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return imgOSSPath;
    }

    /**
     * 根据oss地地址以及目录来删除对应的文件
     * @param direct 要删除的文件所在的目录
     * @param ossPath 图片在oss上的url
     * @throws Exception
     */
    public static void  deleteImageOnOSS(String direct,String ossPath) throws Exception{
        try {
            if(StringUtil.isNotBlank(ossPath)){
                String key = ossPath.substring(ossPath.indexOf(direct), ossPath.indexOf("?"));
                String s = key.replaceAll("%5C", "\\\\");
                AliYunUtil.deleteH5Object(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
