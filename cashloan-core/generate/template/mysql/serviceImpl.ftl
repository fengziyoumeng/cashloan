package ${packageName}.${moduleName}.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ${commonName}.mapper.BaseMapper;
import ${commonName}.service.impl.BaseServiceImpl;
import ${packageName}.${moduleName}.mapper.${ClassName}Mapper;
import ${packageName}.${moduleName}.domain.${ClassName};
import ${packageName}.${moduleName}.service.${ClassName}Service;


/**
 * ${functionName}ServiceImpl
 * 
 * @author ${classAuthor}
 * @version 1.0.0
 * @date ${classDate}
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("${className}Service")
public class ${ClassName}ServiceImpl extends BaseServiceImpl<${ClassName}, Long> implements ${ClassName}Service {
	
    private static final Logger logger = LoggerFactory.getLogger(${ClassName}ServiceImpl.class);
   
    @Resource
    private ${ClassName}Mapper ${className}Mapper;

	@Override
	public BaseMapper<${ClassName}, Long> getMapper() {
		return ${className}Mapper;
	}
	
}