package com.rongdu.cashloan.cl.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wubin on 2017/5/5.
 */
public class FileUtil {

    public static void download(String fileName, HttpServletRequest httpServletRequest, HttpServletResponse response){

        String filePath = httpServletRequest.getSession().getServletContext().getRealPath("File");// 获取真实路径
        String downloadFilename = fileName;// 在下载框默认显示的文件名
        try {
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 对默认下载的文件名编码。不编码的结果就是，在客户端下载时文件名乱码
        File file = new File(filePath+System.getProperty("file.separator")+fileName);
        if (file.exists()) {
            // 写明要下载的文件的大小
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename="
                    + downloadFilename);// 设置在下载框默认显示的文件名
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
            // 读出文件到response
            // 这里是先需要把要把文件内容先读到缓冲区
            // 再把缓冲区的内容写到response的输出流供用户下载
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        fileInputStream);
                byte[] b = new byte[bufferedInputStream.available()];
                bufferedInputStream.read(b);
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(b);
                // 关闭流
                bufferedInputStream.close();
                outputStream.flush();
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("文件不存在");
        }
    }

    public static String StringToNumber(String s){
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.replaceAll("").trim();
    }

    //递归删除文件夹
    public static boolean deleteFile(File file) {
        if (file.exists()) {//判断文件是否存在
            if (file.isFile()) {
                //判断是否是文件
                file.delete();//删除文件
            } else if (file.isDirectory()) {//否则如果它是一个目录
                File[] files = file.listFiles();//声明目录下所有的文件 files[];
                for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
                    deleteFile(files[i]);//把每个文件用这个方法进行迭代
                }
                file.delete();//删除文件夹
            }
            return  true;
        } else {
            System.out.println("所删除的文件不存在");
            return false;
        }
    }

    // 将指定位置已存在的文件在浏览器上自动以流的方式下载,targetFile（E://channel.xls）,fileName（channel.xls）
    public static void downloadByte(String targetFile,String fileName,HttpServletResponse response){
        File file = new File(targetFile);
        if (file.exists()) {
            // 写明要下载的文件的大小
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "attachment;filename="
                    + fileName);// 设置在下载框默认显示的文件名
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
            // 读出文件到response
            // 这里是先需要把要把文件内容先读到缓冲区
            // 再把缓冲区的内容写到response的输出流供用户下载
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        fileInputStream);
                byte[] b = new byte[bufferedInputStream.available()];
                bufferedInputStream.read(b);
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(b);
                // 关闭流
                bufferedInputStream.close();
                outputStream.flush();
                outputStream.close();
                FileUtil.deleteFile(file);
                System.out.println("删除"+fileName+"成功");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println(fileName+"文件不存在");
        }
    }
}
