package com.rongdu.cashloan.api.controller;


import com.rongdu.cashloan.cl.domain.ProblemGeneral;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.cl.service.IProblemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
@CrossOrigin
@RequestMapping("/api/problem")
public class ProblemController {

    @Resource
    private IProblemService problemService;

    @RequestMapping("/info")
    public void getProblemGenerals( HttpServletResponse response ){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<ProblemGeneral> problemGenerals = problemService.getListProblemGeneral();
            result.put(Constant.RESPONSE_DATA, problemGenerals);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
