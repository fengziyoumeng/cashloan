package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.CategoryImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryImageService {

    List<CategoryImage> selectAll();

    void deleteCategoryById(Long id);

    void saveOrUpdate(String data)  throws Exception;

    List<CategoryImage> getCategoryImageList(Integer typeValue)  throws Exception;

    String uploadCategoryById(String realPath,MultipartFile image,String fileName);

}
