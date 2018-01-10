package com.rongdu.cashloan.cl.service;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.domain.HomeSort;
import com.rongdu.cashloan.cl.page.PageResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Map;

public interface ClFlowInfoService {


    List<ClFlowInfo> getAllProdctList(Map<String,Object> params) throws Exception;

    String saveOrUpdate(ClFlowInfo flowInfoModel, String imgPath, String imgName, String tempImgPath, String opType) throws Exception;

    void getPlatFormClick(String code) throws Exception;


    boolean channelDelete(Long id,String code,String imageName) throws Exception;

    String savePic(String realPath,MultipartFile image,String fileName);

    int getThisAmount(int i, String code);
    List<ClFlowInfo> getProdctList(Map<String, Object> searchMap) throws Exception;

    List<ClFlowInfo> getHot() throws Exception;

    PageResult getHotList(int currentPage, int pageSize) throws Exception;

    List<ClFlowInfo> getAll(int current, Map param) throws Exception;

    ClFlowInfo getDetail(Long id,String pCode) throws Exception;

    boolean getUrl(long id, String pCode) throws Exception;

    /*int getNum(String pCode);*/

    Page<ClFlowInfo>  getAllProdctListForUV(Map<String, Object> params, int currentPage, int pageSize) throws Exception;

    List<ClFlowInfo> getAllPCode();

    void update(ClFlowInfo info);

    List<ClFlowInfo> getAllPCodeForYesterday();

    List<ClFlowInfo> getAllInfo();

    List<ClFlowInfo> getListByType(Integer type);

    List<HomeSort> getShowPicList();

    String getTagByType(Integer type);

    Map<String, Object> getAmountClick(String code);

}
