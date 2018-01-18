package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.CompanyProd;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyProdService {

    List<CompanyProd> selectAll();

    void deleteCategoryById(Long id,String imgPath);

    void saveOrUpdate(String data)  throws Exception;

    String uploadCategoryById(String realPath, MultipartFile image, String fileName);

}
