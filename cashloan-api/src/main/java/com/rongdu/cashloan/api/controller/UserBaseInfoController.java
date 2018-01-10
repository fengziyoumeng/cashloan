package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.domain.UserAuth;
import com.rongdu.cashloan.cl.model.FileTypeUtil;
import com.rongdu.cashloan.cl.model.UploadFileRes;
import com.rongdu.cashloan.cl.model.UserAuthModel;
import com.rongdu.cashloan.cl.service.ClBorrowService;
import com.rongdu.cashloan.cl.service.UserAuthService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.exception.BussinessException;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.core.domain.UserBaseInfo;
import com.rongdu.cashloan.core.model.UserWorkInfoModel;
import com.rongdu.cashloan.core.service.CloanUserService;
import com.rongdu.cashloan.core.service.UserBaseInfoService;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * 用户详情表Controller
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 11:08:04 
 * Copyright 杭州民华金融信息服务有限公司 arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Controller
@Scope("prototype")
public class UserBaseInfoController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(UserBaseInfoController.class);

	@Resource
	private UserBaseInfoService userBaseInfoService;

	@Resource
	private UserAuthService userAuthService;

	@Resource
	private ClBorrowService clBorrowService;

	@Resource
	private CloanUserService cloanUserService;


	@Resource
	DataSourceTransactionManager transactionManager;

	@Resource
	private SysDictService sysDictService;
	
	/**
	 * @description 根据userId获取用户信息
	 * @param userId
	 * @author chenxy
	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/api/act/mine/userInfo/getUserInfo.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserInfo(
			@RequestParam(value = "userId", required = true) String userId) {
		//wwpwan 上线时修改回来
		String serverHost = Global.getValue("server_host"); //生产
//		String serverHost = Global.getValue("192.168.2.109:8080"); //测试

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId",Long.parseLong( userId ) );
		UserBaseInfo info = userBaseInfoService.findSelective(paramMap);
		// if (null != info && null != info.getLivingImg()) {
		if (null != info ) {
			info.setLivingImg(serverHost + "/readFile.htm?path=" + info.getLivingImg());
			info.setFrontImg(serverHost + "/readFile.htm?path=" + info.getFrontImg());
			info.setBackImg(serverHost + "/readFile.htm?path=" + info.getBackImg());
			info.setOcrImg(serverHost + "/readFile.htm?path=" + info.getOcrImg());
		}
		return JsonUtil.newJson().addData(Constant.RESPONSE_DATA, info).toJson().toJSONString();
	}

	/**
	 * 查询用户工作信息
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/api/act/mine/userInfo/getWorkInfo.htm", method = RequestMethod.GET)
	public void getWorkInfo(
			@RequestParam(value = "userId", required = true) Long userId) {
		UserWorkInfoModel info = userBaseInfoService.getWorkInfo(userId);
		if (StringUtil.isNotBlank(info.getWorkingImg())) {
			info.setWorkImgState(UserWorkInfoModel.WORK_IMG_ADDED);
		} else {
			info.setWorkImgState(UserWorkInfoModel.WORK_IMG_NO_ADD);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, info);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * 查询用户工作信息
	 * 
	 * @param userId
	 */
	@RequestMapping(value = "/api/act/mine/userInfo/getWorkImg.htm", method = RequestMethod.GET)
	public void getWorkImg(@RequestParam(value = "userId", required = true) Long userId) {
		String serverHost = Global.getValue("server_host");

		UserWorkInfoModel info = userBaseInfoService.getWorkInfo(userId);
		String workImgPath = info.getWorkingImg();
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotBlank(workImgPath)) {
			String[] imgArray = workImgPath.split(";");

			for (int i = 0; i < imgArray.length; i++) {
				if (StringUtil.isNotBlank(imgArray[i])) {
					list.add(serverHost + "/readFile.htm?path=" + imgArray[i]);
				}
			} 
		}

		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("list", list);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, listMap);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response, result);
	}

	/**
	 * @description 根据userId获取用户姓名
	 * @param userId
	 * @author chenxy
	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/api/act/mine/userInfo/getUserName.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getUserName(
			@RequestParam(value = "userId", required = true) String userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserBaseInfo info = userBaseInfoService.findSelective(paramMap);
		paramMap.clear();
		Map<String, Object> temp = new HashMap<String, Object>();
		temp.put("name", info.getRealName());
		return JsonUtil.newJson().addData(Constant.RESPONSE_DATA, temp).toJson().toJSONString();
	}

	/**
	 * @description 根据type获取相应的字典项
	 * @param type
	 * @author chenxy
	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/api/act/dict/list.htm", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDicts(@RequestParam(value = "type") String type) {
		Map<String, Object> dicList = new HashMap<String, Object>();
		if (type != null && type != "") {
			String[] types = type.split(",");
			for (int i = 0; i < types.length; i++) {
				try {


					List<Map<String,Object>> temp = sysDictService.getDictsCache(types[i]);
					if(temp!=null && temp.size()>0){
						for(Map<String,Object> dicDetailMap : temp){
							String oldValue =  dicDetailMap.get("value")==null ?"":dicDetailMap.get("value").toString();
							String oldText =  dicDetailMap.get("text")==null ?"":dicDetailMap.get("text").toString();
							dicDetailMap.put("code",oldValue);
							dicDetailMap.put("value",oldText);
						}
					}

					dicList.put(StringUtil.clearUnderline(types[i]) + "List", sysDictService.getDictsCache(types[i]));
				} catch (ServiceException e) {
					logger.error("获取字典明细失败",e);
				}
			}
		}
		if (!dicList.isEmpty()) {
			return JsonUtil.newJson().addData(Constant.RESPONSE_DATA, dicList).toJson().toJSONString();
		} else {
			return JsonUtil.newFailJson().toJson().toJSONString();
		}
	}
	


	@RequestMapping(value = "/api/act/mine/workInfo/saveOrUpdate.htm", method = RequestMethod.POST)
	public void workInfoSave(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "companyName", required = false) String companyName,
			@RequestParam(value = "companyPhone", required = false) String companyPhone,
			@RequestParam(value = "companyAddr", required = false) String companyAddr,
			@RequestParam(value = "companyDetailAddr", required = false) String companyDetailAddr,
			@RequestParam(value = "companyCoordinate", required = false) String companyCoordinate,
			@RequestParam(value = "workingYears", required = false) String workingYears) {

		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", Long.parseLong( userId));
		UserBaseInfo info = userBaseInfoService.findSelective(userMap);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("companyName", companyName);
		paramMap.put("companyPhone", companyPhone);
		paramMap.put("companyAddr", companyAddr);
		paramMap.put("companyDetailAddr", companyDetailAddr);
		paramMap.put("companyCoordinate", companyCoordinate);
		paramMap.put("workingYears", workingYears);
		paramMap.put("id", info.getId());
		paramMap.put("userId",Long.parseLong( userId ));
		boolean flag = userBaseInfoService.updateSelective(paramMap);

		if (flag) {
			Map<String, Object> authMap = new HashMap<String, Object>();
			authMap.put("userId", Long.parseLong(userId ));
			authMap.put("workInfoState", UserAuthModel.STATE_VERIFIED);
			userAuthService.updateByUserId(authMap);
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (flag) {
			resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");
		} else {
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "系统错误，保存失败");
		}
		ServletUtils.writeToResponse(response, resultMap);
	}

	@RequestMapping(value = "/api/act/mine/workInfo/workImgSave.htm", method = RequestMethod.POST)
	public void workImgSava(
			@RequestParam(value = "userId", required = true) String userId,
			@RequestParam(value = "workImgFir", required = false) MultipartFile workImgFir,
			@RequestParam(value = "workImgSec", required = false) MultipartFile workImgSec,
			@RequestParam(value = "workImgThr", required = false) MultipartFile workImgThr) {
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("userId", userId);
		UserBaseInfo info = userBaseInfoService.findSelective(userMap);

		// 判断是否已经添加过 ，如果已经添加过 则将原图片路径添加过
		String workImg = "";
		if (null != info && StringUtil.isNotBlank(info.getWorkingImg())) {
			workImg = StringUtil.isNull(info.getWorkingImg());
		}

		StringBuilder buffer = new StringBuilder(workImg);

		if (workImgFir != null) {
			List<UploadFileRes> list = new LinkedList<>();
			response.setContentType("text/html;charset=utf8");
			saveMultipartFile(list, workImgFir);
			buffer.append(list.get(0).getResPath()).append(";");
		}

		if (workImgSec != null) {
			List<UploadFileRes> list = new LinkedList<>();
			response.setContentType("text/html;charset=utf8");
			saveMultipartFile(list, workImgSec);
			buffer.append(list.get(0).getResPath()).append(";");
		}

		if (workImgThr != null) {
			List<UploadFileRes> list = new LinkedList<>();
			response.setContentType("text/html;charset=utf8");
			saveMultipartFile(list, workImgThr);
			buffer.append(list.get(0).getResPath()).append(";");
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("workingImg", StringUtil.isNull(buffer));
		if (info != null) {
			paramMap.put("id", info.getId());
		}
		boolean flag = userBaseInfoService.updateSelective(paramMap);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (flag) {
			resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "保存成功");
		} else {
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "系统错误，保存失败");
		}
		ServletUtils.writeToResponse(response, resultMap);
	}

	private void saveMultipartFile(List<UploadFileRes> list, MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			UploadFileRes model = save(file);
			list.add(model);
		}
	}

	private UploadFileRes save(MultipartFile file) {
		UploadFileRes model = new UploadFileRes();
		model.setCreateTime(DateUtil.getNow());

		// 文件名称
		String picName = file.getOriginalFilename();

		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File newFile = (File) fi.getStoreLocation();
		logger.debug("图片----------"+newFile);
		// 文件格式
		String fileType = FileTypeUtil.getFileType(newFile);
		if (StringUtil.isBlank(fileType) || !FileTypeUtil.isImage(newFile, fileType)) {
//			model.setErrorMsg("图片格式错误或内容不规范");
//			return model;
			throw new BussinessException("图片格式错误或内容不规范");
		}
		// 校验图片大小
		Long picSize = file.getSize();
		if (picSize.compareTo(20971520L) > 0) {
			throw new BussinessException("文件超出20M大小限制");
			//model.setErrorMsg("文件超出20M大小限制");
			//return model;
		}
		// 保存文件
		//wwpwan 上线后修改
		String s=File.separator;
//		String filePath = s+"data"+s+"image"+s + fileType + s + System.currentTimeMillis() + s + picName;
//		String filePath = "/mnt/home/yqb/yqb_upload"+s+"image"+s + fileType + s + System.currentTimeMillis() + s + picName;  //生产路径
		String filePath = "/Users/wangxinbo/Documents/temp"+s+"image" + s + System.currentTimeMillis() + s + picName;

		if(s.equals("\\")){
			filePath="D:"+filePath;
		}
		File files = new File(filePath);
		if (!files.exists()) {
			try {
				files.mkdirs();
			} catch (Exception e) {
				logger.error("照片目录不存在", e);
//				model.setErrorMsg("文件目录不存在");
//				return model;
				throw new BussinessException("数据保存失败");
			}
		}
		try {
			file.transferTo(files);
		} catch (IllegalStateException | IOException e) {
			logger.error("照片保存失败", e);
			throw new BussinessException("数据保存失败");

		}
		// 转存文件
		model.setResPath(filePath);
		model.setFileName(picName);
		model.setFileFormat(fileType);
		model.setFileSize(new BigDecimal(picSize));
		return model;
	}




	/**
	 * @description 获取验证状态
	 * @param userId
	 * @author chenxy
	 * @return void
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/api/act/mine/userAuth/getUserAuth.htm", method = RequestMethod.GET)
	public void getUserAuth(
			@RequestParam(value = "userId", required = true) String userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", Long.parseLong( userId ) );
		UserAuth userAuth = null;

		//begin pantheon 20170615 新版用户的芝麻状态取决于芝麻状态和同盾状态
		String newVersion = request.getHeader("xiaoe");
		if (!StringUtil.isEmpty(newVersion) && "1".equals(newVersion)) {
			userAuth = userAuthService.getUserAuthNewVersion(paramMap);
		}else {
			userAuth = userAuthService.getUserAuth(paramMap);
		}
		//end

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_DATA, userAuth);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}
	
	
	@RequestMapping(value = "/api/act/mine/userAuth/getAuthImgLogo.htm", method = RequestMethod.GET)
	public void getAuthImgLogo() {
		
		String serverHost = Global.getValue("server_host");
		String path = request.getSession().getServletContext().getRealPath("/");
		StringBuilder buffer = new StringBuilder(path)
				.append(File.separatorChar).append("static")
				.append(File.separatorChar).append("images")
				.append(File.separator).append("authImgLogo.png");
		
		Map<String, Object> data = new HashMap<>();
		String filePath = StringUtil.isNull(buffer);
		data.put("authImgLogo", serverHost + "/readFile.htm?path=" + filePath);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response, result);
	}

	private File getRemoteFile(String path, String msg) throws IOException {
		String dirPath = request.getSession().getServletContext().getRealPath("/") +File.separator+"pic"+File.separator;
		File temp = new File(dirPath);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		File file = new File("");
		URL url = new URL(path);
		FileOutputStream out = null;
		URLConnection conn = url.openConnection();
		try {
			InputStream is = conn.getInputStream();
			file = new File(dirPath + msg + ".jpg");
			if (!file.exists())
				file.createNewFile();
			out = new FileOutputStream(file);
			int c;
			byte buffer[] = new byte[is.available()];
			while ((c = is.read(buffer)) != -1) {
				for (int i = 0; i < c; i++)
					out.write(buffer[i]);
			}
			is.close();
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return file;
	}

	private void removeFile(String filePath){
		File f=new File(filePath);
		if(f.exists()){
			f.delete();
		}
	}
	
}