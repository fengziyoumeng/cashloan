package com.rongdu.cashloan.api.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.system.domain.SysDict;
import com.rongdu.cashloan.system.domain.SysDictDetail;
import com.rongdu.cashloan.system.permission.annotation.RequiresPermission;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictDetailService;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 数据字典管理
 * 
 * @version 1.0
 * @author wubin
 */
@Scope("prototype")
@Controller
@CrossOrigin
@RequestMapping("/api/dict")
public class DictController extends BaseController {

	@Resource
	private SysDictService sysDictService;

	@Resource
	private SysDictDetailService sysDictDetailService;

	/**
	 * 传入arc_sys_dict_detail表的parentId
	 * @param response
	 * @param parentId
	 */
	@RequestMapping("/query/type/list")
	public void queryProdBdata(HttpServletResponse response,String parentId){
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("parentId",parentId);
		try {
			List<SysDictDetail> sysDictDetails = sysDictDetailService.getItemCodeAndVlueByParentId(paramMap);
			result.put(Constant.RESPONSE_DATA, sysDictDetails);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		}catch (Exception e){
			result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "查询失败");
		}
		ServletUtils.writeToResponse(response,result);
	}
}
