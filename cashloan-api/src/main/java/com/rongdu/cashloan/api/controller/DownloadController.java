package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wubin
 * @version 1.0.0
 * @date 2017年11月22日 16:49:51
 */

@Scope("prototype")
@Controller
public class DownloadController {
    public static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

    @RequestMapping(value = "/act/downloadApk.htm")
    public void downloadApk(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FileUtil.download("com.minhua.jijiehao.apk",httpServletRequest,httpServletResponse);
    }
}
