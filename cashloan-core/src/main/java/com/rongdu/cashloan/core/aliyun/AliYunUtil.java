package com.rongdu.cashloan.core.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 *
 * 创建阿里云工具类
 */

public class AliYunUtil {
	private static final Logger logger = Logger.getLogger(AliYunUtil.class);
	public  static String ALIVE_IMG_DIR = "alive";
	public static String FRONT_IMG_DIR = "front";
	public static String BACK_IMG_DIR = "back";
	private static	String ENDPOINT = Global.getValue("ENDPOINT");
	private static	String BUCKET_NAME = Global.getValue("BUCKET_NAME");
	private static	String BUCKET_NAMEH5 = "yqb-h5";
	private static	String ACCESSKEYID = Global.getValue("ACCESSKEYID");
	private static	String ACCESSKEYSECRET = Global.getValue("ACCESSKEYSECRET");
	private static OSSClient client = new OSSClient(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);

	/**
	 * 上传文件优化版本
	 * @param dirName 文件路径
	 * @param file
	 * @throws OSSException
	 * @date 2017年9月3日
	 */
	public static  String uploadFile( String dirName,String filename, File file) throws OSSException {
		try {
			if (null == file || file.length() == 0) {
				throw new OSSException("请选择上传的文件");
			}
			String path=dirName+File.separatorChar+filename;
			client.putObject(BUCKET_NAME, path, file);
			return getUrl(path);
		} catch (OSSException e) {
			e.printStackTrace();
			throw new OSSException("上传出错:" + e.getMessage());
		}

	}
	/**
	 * 上传Oss H5
	 * @param dirName 上传oss第一目录
	 * @param filename 上传文件的名称
	 * @param file   上传的文件
	 * @throws OSSException
	 * @date 2017年10月28日
	 */
	public static  String uploadH5File( String dirName,String filename, File file) throws OSSException {
		try {
			if (null == file || file.length() == 0) {
				throw new OSSException("请选择上传的文件");
			}
			String path=dirName+"/"+filename;
			client.putObject(BUCKET_NAMEH5, path, file);
			return getH5Url(BUCKET_NAMEH5,path);
		} catch (OSSException e) {
			e.printStackTrace();
			throw new OSSException("上传出错:" + e.getMessage());
		}

	}
    /**
     * 上传文件到公共的bucket优化版本
     * @param dirName 文件路径
     * @param file
     * @throws OSSException
     * @date 2017年10月26日
     */
	public static  String uploadPublicFile(String bucketName, String dirName,String filename, File file) throws OSSException {
		try {
			if (null == file || file.length() == 0) {
				throw new OSSException("请选择上传的文件");
			}
			String path=dirName+File.separatorChar+filename;
			client.putObject(bucketName, path, file);
			return getPublicUrl(bucketName,path);
		} catch (OSSException e) {
			e.printStackTrace();
			throw new OSSException("上传出错:" + e.getMessage());
		}

	}

	/**
	 * 检查文件是否存在优化版本
	 * @param filename 文件名
	 * @throws OSSException
	 * @date 2017年9月3日
	 */
	public static boolean DoesObjectExist(String filename){
		if(filename.startsWith("/")){
			filename =filename.substring(1,filename.length());
		}

		return  client.doesObjectExist(BUCKET_NAME, filename);
	}



	/**
	 * 关闭client 不需要自己关闭  oss会帮助管理
	 * @date 2017年9月3日
	 */
	public static void shutdown() {
		client.shutdown();
	}

	/**
	 * 下载文件优化  调用的是oss的简单下载
	 * @param filename 文件名称
	 * @return BufferedInputStream
	 * @throws OSSException
	 * @date 2017年9月3日
	 */
	public static BufferedInputStream download(String Dirname,String filename) throws OSSException {
		try{
			if(Dirname.startsWith("/")){
				Dirname =Dirname.substring(1,filename.length());
			}
			OSSObject oo = client.getObject(new GetObjectRequest(BUCKET_NAME, Dirname+filename));
			return new BufferedInputStream(oo.getObjectContent());
		}catch (OSSException e){
			e.printStackTrace();
			throw new OSSException("文件下载出错" + e.getMessage());
		}
	}
	public static BufferedInputStream download(String filename) throws OSSException {
		try{
			if(filename.startsWith("/")){
				filename =filename.substring(1,filename.length());
			}

			OSSObject oo = client.getObject(new GetObjectRequest(BUCKET_NAME, filename));
			return new BufferedInputStream(oo.getObjectContent());
		}catch (OSSException e){
			e.printStackTrace();
			throw new OSSException("文件下载出错" + e.getMessage());
		}
	}
	public static void deleteObject(String filePath) throws OSSException {

			try {
				if (StringUtils.isBlank(filePath)) {
					throw new OSSException("请输入删除的文件路径");
				}


				boolean exists = client.doesObjectExist(BUCKET_NAME, filePath);
				if (!exists) {
					throw new OSSException(filePath+"此路径并不存在文件");
				}
				client.deleteObject(BUCKET_NAME, filePath);
			} catch (OSSException e) {
				throw new OSSException("删除出错:" + e.getMessage());
			}


	}
	public static void deleteH5Object(String filePath) throws OSSException {

		try {
			if (StringUtils.isBlank(filePath)) {
				throw new OSSException("请输入删除的文件路径");
			}
			boolean exists = client.doesObjectExist(BUCKET_NAMEH5, filePath);
			if (!exists) {
				throw new OSSException(filePath+"此路径并不存在文件");
			}
			client.deleteObject(BUCKET_NAMEH5, filePath);
		} catch (OSSException e) {
			throw new OSSException("删除出错:" + e.getMessage());
		}


	}
	public static String getUrl(String key) {
		// 设置URL过期时间为10年  3600l* 1000*24*365*10
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		// 生成URL
		URL url = client.generatePresignedUrl(BUCKET_NAME, key, expiration);
		if (url != null) {
			return url.toString();
		}
		return null;
	}
	//获取H5的上传地址
	public static String getH5Url(String bucketName,String key) {
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		// 生成URL
		URL url = client.generatePresignedUrl(bucketName, key, expiration);
		if (url != null) {
			return url.toString();
		}
		return null;
	}
	public static String getPublicUrl(String bucket,String key) {//ENDPOIN
			if (StringUtils.isBlank(bucket)) {
				throw new OSSException("请输入bucket");
			}
		return bucket+"."+ StringUtil.substring(ENDPOINT,7)+"/"+key;
	}


	public static void main(String[] args) {


		try {
			File file =new File("/Users/dinglishi/IdeaProjects/cashloan_rongdu/cashloan-api/src/main/webapp/static/images/button.png");
			//AliYunUtil.uploadFile(MediaType.IMG,file);
			;
			String x =uploadFile("logo","1",file);
			String test = BACK_IMG_DIR+"qwdewf";
			System.out.println("======"+test+"===");
			InputStream fis =  AliYunUtil.download("/mnt/home/yqb/yqb_upload/image/jpeg/1501485029678/1501485000back.jpg");
			System.out.println(fis.toString());
			client.deleteObject(BUCKET_NAME,"ico001.png");
			//	InputStream is =AliYunUtil.download(file.getName());
			//	System.out.println(	AliYunUtil.DoesObjectExist("button.png"));
		} catch (OSSException e) {
			e.printStackTrace();
		}
	}
}
