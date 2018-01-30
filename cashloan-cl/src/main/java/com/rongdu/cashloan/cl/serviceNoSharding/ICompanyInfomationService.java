package com.rongdu.cashloan.cl.serviceNoSharding;

import com.rongdu.cashloan.cl.domain.CompanyInformation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICompanyInfomationService {

    void saveOrUpdate(CompanyInformation companyInformation) throws Exception;

    String uploadCompanyImage(String realPath,MultipartFile image,String fileName);

    List<CompanyInformation> auditList();

    void infoAudit(String data) throws Exception;

    CompanyInformation selectAuditState(Long userId);

}
