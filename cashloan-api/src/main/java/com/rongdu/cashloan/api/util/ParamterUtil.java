package com.rongdu.cashloan.api.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ParamterUtil {

    public static Map<String,Object> getParamterMap(HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap();
        Enumeration paramNames = httpServletRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = httpServletRequest.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

    public static boolean isNull(Map<String, Object> params, String ...names) {
        if(params ==null){
            return true;
        }
        for(String key:names){
            Object value = params.get(key);
            if(value==null ||"".equals(value.toString().trim())){
                return true;
            }
        }
        return false;
    }
}