package com.rongdu.cashloan.cl.util;

public class StateUtil {

    /**
     * 判断是否有该状态
     * @param stateValue 状态码
     * @param value 需要判断的状态值
     * @return
     */
    public static boolean hasState(Integer stateValue,Integer value){
        boolean flag =false;
        if (stateValue !=null && value!=null){
              if((stateValue & value)>0){
                return true;
              }
        }
        return flag ;
    }

    public static Integer addState(Integer stateValue,Integer value){
        if (stateValue!=null && value!=null){
            return stateValue|value;
        }
        return null;
    }

}
