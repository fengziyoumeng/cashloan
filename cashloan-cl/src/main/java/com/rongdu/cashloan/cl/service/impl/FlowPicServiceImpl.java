package com.rongdu.cashloan.cl.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.cl.mapper.FlowPicMapper;
import com.rongdu.cashloan.cl.service.FlowPicService;
import com.rongdu.cashloan.cl.util.FileUtil;
import com.rongdu.cashloan.core.aliyun.AliYunUtil;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 首页轮播图片ServiceImpl
 * 
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-13 19:40:01
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("flowPicService")
public class FlowPicServiceImpl extends BaseServiceImpl<FlowPic, Long> implements FlowPicService {
	
    private static final Logger logger = LoggerFactory.getLogger(FlowPicServiceImpl.class);
   
    @Resource
    private FlowPicMapper flowPicMapper;

	@Resource
	private ShardedJedisClient redisClient;

	@Override
	public BaseMapper<FlowPic, Long> getMapper() {
		return flowPicMapper;
	}

	@Override
	public List<FlowPic> getPic(Integer type) {
		List<FlowPic> pics;
		try{
			pics = (List<FlowPic>)redisClient.getObject(AppConstant.REDIS_KEY_PIC_FLOW_INFO+"type");
			if(pics != null && !pics.isEmpty()){
				return pics;
			}
		}catch (Exception e){
			logger.info("获取redis中的首页轮播图时发生错误"+e);
		}
		pics =  flowPicMapper.getPic(type);
		try{
			if(pics != null && !pics.isEmpty()){
				redisClient.setObject(AppConstant.REDIS_KEY_PIC_FLOW_INFO+"type",pics);
			}
		}catch (Exception e){
			logger.info("将轮播图存储到redis时发生错误"+e);
		}
		return pics;
	}

	@Override
	public List<FlowPic> getAllBanner() {
//		HashMap<String, Object> param = new HashMap<>();
//		param.put("pType",pType);
		List<FlowPic> picList = flowPicMapper.getAllBanner();
		for (FlowPic flowPic : picList) {
				if (flowPic.getwUrl() != null && StringUtil.isNotEmpty(flowPic.getwUrl())) {
					if (flowPic.getPCode() != null && StringUtil.isNotEmpty(flowPic.getPCode())) {
						flowPic.setSkipType(1);
					} else {
						flowPic.setSkipType(2);
					}
				} else {
					if (flowPic.getPCode() != null && StringUtil.isNotEmpty(flowPic.getPCode())) {
						flowPic.setSkipType(3);
					} else {
						flowPic.setSkipType(0);
					}
				}
		}
		return picList;
	}

	@Override
	public int saveOrUpdate(FlowPic flowPic) {
		Long id = flowPic.getId();
		String bannerPath = null;
		String skipImgPath = null;
		try {
			//上传的文件不为空，文件保存至Oss上
			if(StringUtil.isNotEmpty(flowPic.getUrl())){
				File file =new File(flowPic.getUrl());
				if(file.exists()){//判断服务器上是否已经有图片，有则认为是重新上传，无则认为没有修改图片，不再上传oss
					bannerPath = AliYunUtil.uploadH5File("bannerImage/", UUID.randomUUID().toString()+".png",file);
					flowPic.setUrl(bannerPath);
					logger.info("===>OSS图片地址："+bannerPath);
				}
				//上传成功后删除本地文件
				//String path = request.getRealPath("/")+"flowPlatFormImg";
				File file1 =new File(flowPic.getUrl());
				if (FileUtil.deleteFile(file1)) {
					logger.info("===>上传后删除本地文件成功" + file1);
				}
			}
			if(StringUtil.isNotEmpty(flowPic.getwUrl())){
				File file =new File(flowPic.getwUrl());
				if(file.exists()){//判断服务器上是否已经有图片，有则认为是重新上传，无则认为没有修改图片，不再上传oss
					skipImgPath = AliYunUtil.uploadH5File("bannerImage/", UUID.randomUUID().toString(),file);
					flowPic.setwUrl(skipImgPath);
					logger.info("===>OSS图片地址："+bannerPath);
				}
				//上传成功后删除本地文件
				File file1 =new File(flowPic.getwUrl());
				if (FileUtil.deleteFile(file1)) {
					logger.info("===>上传后删除本地文件成功" + file1);
				}
			}
			//删除redis中对应的缓存
			redisClient.del(AppConstant.REDIS_KEY_PIC_FLOW_INFO);
		}catch (Exception e){
			e.printStackTrace();
		}

		int ret = 0;
		try {
			if (id == null) {
				ret =  flowPicMapper.save(flowPic);
			} else {
				ret =  flowPicMapper.update(flowPic);
			}
		}catch (Exception e){
			logger.info("banner保存或更新失败"+e);
			throw e;
		}
		return ret;
	}

	@Override
	public int deleteById(Long id) {
		return flowPicMapper.deleteById(id);
	}
}