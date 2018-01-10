package com.rongdu.cashloan.api.user.bean;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rongdu.cashloan.core.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongdu.cashloan.api.controller.UserBaseInfoController;
import com.rongdu.cashloan.core.common.util.StringUtil;

/**
 * Created by lsk on 2016/7/29.
 */
@SuppressWarnings({ "rawtypes", "unused" })
public abstract class AppLoginedActionWraper extends AppAbsActionWrapper {
	
	public static final Logger logger = LoggerFactory.getLogger(AppLoginedActionWraper.class);

    public AppLoginedActionWraper(HttpServletResponse _resp, HttpServletRequest _req) {
        super(_resp, _req);
    }


    private String getSignParam(String name) {
        String value = _req.getParameter(name);
        if (StringUtil.isEmpty(value)) {
            value = _req.getHeader(name);
        }
        return value;
    }

    @Override
    public Object doAction()  {
        Ticket ticket = (Ticket) _req.getSession().getAttribute("userData");
        logger.info("ticket信息>>>"+ticket);
        String userId = ticket.getUserId();
        return doAction(ticket, userId);
    }


    public abstract Object doAction(Ticket ticket, String userId) ;

}
