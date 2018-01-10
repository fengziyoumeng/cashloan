/********************************************
 * 文件名称: PropertiesUtil <br/>
 * 系统名称: 直销银行V1.0
 * 模块名称: WEB业务平台帐户类
 * 软件版权: 信雅达系统工程股份有限公司
 * 功能说明: TODO ADD FUNCTION. <br/>
 * 系统版本: 1.0.0.1
 * 开发人员:  Terrance
 * 开发时间: 2014年12月15日 下午3:36:26 <br/>
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期    修改人员    修改说明
 * V3.0.0.2 20130530-01  XXXX        提示报错 M201305300011
 *********************************************/

package com.rongdu.cashloan.manage.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class SftpPropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(SftpPropertiesUtil.class);
    private static  Properties props = new Properties();;

    private SftpPropertiesUtil() {}

    static{
        InputStream is = SftpPropertiesUtil.class.getClassLoader().getResourceAsStream("config/vsftpd.properties");
        try {
        	props.load(is);
        } catch (IOException e) {
            logger.error("读取配置文件出错", e);
        }
    }

 
    /**
     * 获取某个属性
     */
    public static String getVal(String key) {
    	return props.getProperty(key);
    }

    /**
     * 获取所有属性，返回一个map,不常用
     * 可以试试props.putAll(t)
     */
	public static Map<String,Object> getAllVal() {
        Map<String,Object> map = new HashMap<String,Object>();
        Enumeration<?> enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

//    /**
//     * 写入properties信息
//     */
//    public void writeProperties(String key, String value) throws Exception {
//        OutputStream fos = new FileOutputStream(fileName);
//        props.setProperty(key, value);
//        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
//        props.store(fos, "『comments』Update key：" + key);
//    }
    public static void main(String[] args) throws Exception {
		System.out.println(getAllVal().get("netUrl"));
		System.err.println(getVal("netUrl"));
	}
}
