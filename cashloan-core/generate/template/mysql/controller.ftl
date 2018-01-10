package ${packageName}.${moduleName}.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ${commonName}.web.controller.BaseController;
import ${packageName}.${moduleName}.service.${ClassName}Service;

 /**
 * ${functionName}Controller
 * 
 * @author ${classAuthor}
 * @version 1.0.0
 * @date ${classDate}
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Controller
public class ${ClassName}Controller extends BaseController {

	@Resource
	private ${ClassName}Service ${className}Service;

}
