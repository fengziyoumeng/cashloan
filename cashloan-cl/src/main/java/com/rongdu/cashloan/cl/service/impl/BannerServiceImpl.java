package com.rongdu.cashloan.cl.service.impl;


import com.rongdu.cashloan.cl.domain.BannerInfo;
import com.rongdu.cashloan.cl.mapper.BannerInfoMapper;
import com.rongdu.cashloan.cl.service.BannerService;
import com.rongdu.cashloan.cl.util.ImageUploadUtil;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
 
@Service("bannerServiceImpl")
public class BannerServiceImpl implements BannerService {
	
    private static final Logger logger = LoggerFactory.getLogger(BannerServiceImpl.class);
   
    @Resource
    private BannerInfoMapper bannerInfoMapper;

	@Resource
	private ShardedJedisClient redisClient;

	

	@Override
	public List<BannerInfo> getAllBanner() {
		List<BannerInfo> picList = bannerInfoMapper.selectAll();
		return picList;
	}

	@Override
	public void saveOrUpdate(String data) throws Exception{

		BannerInfo bannerInfo = JsonUtil.parse(data,BannerInfo.class);
		Map parse = JsonUtil.parse(data, Map.class);
		String imgPath = parse.get("path")!=null ? parse.get("path").toString():"";
		try{
			String oldPath = bannerInfo.getBanner_url();
			String ossPath = ImageUploadUtil.uploadOSSDeleteTemp(imgPath, "Banner", oldPath);
			if(StringUtil.isNotBlank(ossPath)){
				bannerInfo.setBanner_url(ossPath);
			}
			if(bannerInfo.getId()==null){
				bannerInfoMapper.insert(bannerInfo);
			}else {
				bannerInfoMapper.updateByPrimaryKey(bannerInfo);
			}
		}catch (Exception e){
			logger.info("操作失败",e);
			throw e;
		}
	}

	@Override
	public void deleteById(Long id,String imgPath)throws Exception {
		try{
			bannerInfoMapper.deleteByPrimaryKey(id);
			ImageUploadUtil.deleteImageOnOSS("Banner",imgPath);
		}catch (Exception e){
			logger.info("删除失败",e);
			throw e;
		}
	}
}