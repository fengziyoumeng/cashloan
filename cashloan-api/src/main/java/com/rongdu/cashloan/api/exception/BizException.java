package com.rongdu.cashloan.api.exception;


import java.util.HashMap;

/**
 *  业务错误处理类
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -5875371379845226068L;

    /**
     * 具体异常码
     */
    protected String code;

    /**
     * 异常信息
     */
    protected String msg;


    /**
     *  返回错误是否加密
     */
    protected Boolean isEncrypt = false;


    protected HashMap<String,Object> resultMap;

    public BizException() {
        super();
    }

    public BizException(String code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(HashMap<String,Object> resultMap, Boolean isEncrypt) {
        super(resultMap.get("retmsg")==null?"":resultMap.get("retmsg").toString());
        this.isEncrypt = isEncrypt;
        this.resultMap = resultMap;
    }

    public BizException(HashMap<String,Object> resultMap, Boolean isEncrypt, Throwable cause) {
        super(resultMap.get("retmsg")==null?"":resultMap.get("retmsg")==null?"":resultMap.get("retmsg").toString(), cause);
        this.isEncrypt = isEncrypt;
        this.resultMap = resultMap;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public HashMap<String, Object> getResultMap() {
        return resultMap;
    }

    public Boolean getEncrypt() {
        return isEncrypt;
    }
}
