package com.rongdu.cashloan.cl.domain;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSONArray;
import com.rongdu.cashloan.system.domain.SysDictDetail;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CompanyProdDetail implements Serializable {
    /**
     * 
     */
    private Long id;

    private Long proc_id;

    /**
     * B圈产品类型小分类
     */
    private Integer type;

    /**
     * 关联b_company_prod的type
     */
    private Integer cp_type;

    /**
     * 企业id
     */
    private Long org_id;

    /**
     * 产品名称
     */
    private String proc_name;

    /**
     * 产品logo
     */
    private String logo_path;

    /**
     * 官方网址（公司网址）
     */
    private String official_url;

    /**
     * 产品标志（0-普通产品，1-推荐产品）
     */
    private Integer proc_flag;

    /**
     * 是否上线（0-下线，1-上线）
     */
    private Integer status;

    /**
     * 二级排序（针对二级类型排序：对于推荐产品proc_flag=1来说，以数字123...从上往下排序；对于非推荐产品来说：对于parent_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     */
    private Integer sort;

    /**
     * 
     */
    private String audit_person;

    /**
     * 审核状态 1-资料审核中2-审核通过3-审核拒绝
     */
    private Integer audit_state;

    /**
     * 
     */
    private String audit_message;

    /**
     * 产品服务介绍
     */
    private String proc_info;

    /**
     * 运营信息
     */
    private String operativeInfos;
    private List<OperativeInfo> OperativeInfoList;
    private CompanyProd companyProd;
    private CompanyInformation companyInfo;
    private SysDictDetail sysDictDetail;

    public SysDictDetail getSysDictDetail() {
        return sysDictDetail;
    }

    public void setSysDictDetail(SysDictDetail sysDictDetail) {
        this.sysDictDetail = sysDictDetail;
    }

    /**
     * 企业地址
     */
    private String companyAddress;

    /**
     * 营销信息
     */
    private String p_message;

    /**
     * 注册资金
     */
    private String registeredCapital;

    /**
     * 公司简介
     */
    private String introduction;

    /**
     * 审核时间
     */
    private Date audit_time;

    /**
     * 创建时间
     */
    private Date create_time;
    /**
     * 拒绝理由
     */
    private List<Integer> regectMessage;

    /**
     * 更新时间
     */
    private Date update_time;

    private Long show_click_num;

    private String flag_msg;
    /**
     * b_company_prod_detail
     */
    private static final long serialVersionUID = 1L;

    public String getFlag_msg() {
        return flag_msg;
    }

    public void setFlag_msg(String flag_msg) {
        this.flag_msg = flag_msg;
    }

    public Long getShow_click_num() {
        return show_click_num;
    }

    public void setShow_click_num(Long show_click_num) {
        this.show_click_num = show_click_num;
    }

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    public String getP_message() {
        return p_message;
    }

    public void setP_message(String p_message) {
        this.p_message = p_message;
    }

    /**
     * 
     * @param id 
     */


    public void setId(Long id) {
        this.id = id;
    }

    public Long getProc_id() {
        return proc_id;
    }

    public void setProc_id(Long proc_id) {
        this.proc_id = proc_id;
    }

    public List<Integer> getRegectMessage() {
        return regectMessage;
    }

    public void setRegectMessage(List<Integer> regectMessage) {
        this.regectMessage = regectMessage;
    }

    /**
     * B圈产品类型小分类
     * @return type B圈产品类型小分类
     */
    public Integer getType() {
        return type;
    }

    /**
     * B圈产品类型小分类
     * @param type B圈产品类型小分类
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 关联b_company_prod的type
     * @return cp_type 关联b_company_prod的type
     */
    public Integer getCp_type() {
        return cp_type;
    }

    /**
     * 关联b_company_prod的type
     * @param cp_type 关联b_company_prod的type
     */
    public void setCp_type(Integer cp_type) {
        this.cp_type = cp_type;
    }

    /**
     * 企业id
     * @return org_id 企业id
     */
    public Long getOrg_id() {
        return org_id;
    }

    /**
     * 企业id
     * @param org_id 企业id
     */
    public void setOrg_id(Long org_id) {
        this.org_id = org_id;
    }

    /**
     * 产品名称
     * @return proc_name 产品名称
     */
    public String getProc_name() {
        return proc_name;
    }

    /**
     * 产品名称
     * @param proc_name 产品名称
     */
    public void setProc_name(String proc_name) {
        this.proc_name = proc_name == null ? null : proc_name.trim();
    }



    /**
     * 产品logo
     * @return logo_path 产品logo
     */
    public String getLogo_path() {
        return logo_path;
    }

    /**
     * 产品logo
     * @param logo_path 产品logo
     */
    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path == null ? null : logo_path.trim();
    }

    /**
     * 官方网址（公司网址）
     * @return official_url 官方网址（公司网址）
     */
    public String getOfficial_url() {
        return official_url;
    }

    /**
     * 官方网址（公司网址）
     * @param official_url 官方网址（公司网址）
     */
    public void setOfficial_url(String official_url) {
        this.official_url = official_url == null ? null : official_url.trim();
    }

    /**
     * 产品标志（0-普通产品，1-推荐产品）
     * @return proc_flag 产品标志（0-普通产品，1-推荐产品）
     */
    public Integer getProc_flag() {
        return proc_flag;
    }

    /**
     * 产品标志（0-普通产品，1-推荐产品）
     * @param proc_flag 产品标志（0-普通产品，1-推荐产品）
     */
    public void setProc_flag(Integer proc_flag) {
        this.proc_flag = proc_flag;
    }

    /**
     * 是否上线（0-下线，1-上线）
     * @return status 是否上线（0-下线，1-上线）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 是否上线（0-下线，1-上线）
     * @param status 是否上线（0-下线，1-上线）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 二级排序（针对二级类型排序：对于推荐产品proc_flag=1来说，以数字123...从上往下排序；对于非推荐产品来说：对于parent_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     * @return sort 二级排序（针对二级类型排序：对于推荐产品proc_flag=1来说，以数字123...从上往下排序；对于非推荐产品来说：对于parent_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 二级排序（针对二级类型排序：对于推荐产品proc_flag=1来说，以数字123...从上往下排序；对于非推荐产品来说：对于parent_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     * @param sort 二级排序（针对二级类型排序：对于推荐产品proc_flag=1来说，以数字123...从上往下排序；对于非推荐产品来说：对于parent_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 
     * @return audit_person 
     */
    public String getAudit_person() {
        return audit_person;
    }

    /**
     * 
     * @param audit_person 
     */
    public void setAudit_person(String audit_person) {
        this.audit_person = audit_person == null ? null : audit_person.trim();
    }

    /**
     * 审核状态 1-资料审核中2-审核通过3-审核拒绝
     * @return audit_state 审核状态 1-资料审核中2-审核通过3-审核拒绝
     */
    public Integer getAudit_state() {
        return audit_state;
    }

    /**
     * 审核状态 1-资料审核中2-审核通过3-审核拒绝
     * @param audit_state 审核状态 1-资料审核中2-审核通过3-审核拒绝
     */
    public void setAudit_state(Integer audit_state) {
        this.audit_state = audit_state;
    }

    /**
     * 
     * @return audit_message 
     */
    public String getAudit_message() {
        return audit_message;
    }

    /**
     * 
     * @param audit_message 
     */
    public void setAudit_message(String audit_message) {
        this.audit_message = audit_message == null ? null : audit_message.trim();
    }

    /**
     * 产品服务介绍
     * @return proc_info 产品服务介绍
     */
    public String getProc_info() {
        return proc_info;
    }

    /**
     * 产品服务介绍
     * @param proc_info 产品服务介绍
     */
    public void setProc_info(String proc_info) {
        this.proc_info = proc_info == null ? null : proc_info.trim();
    }

    public String getOperativeInfos() {
        return operativeInfos;
    }

    public void setOperativeInfos(String operativeInfos) {
        System.out.println("operativeInfos前："+operativeInfos);
        if(operativeInfos.contains("|")){
            operativeInfos = operativeInfos.replace("|",",");
        }
        System.out.println("operativeInfos后："+operativeInfos);
        this.operativeInfos = operativeInfos;
        setOperativeInfoList(JSONArray.parseArray(operativeInfos,OperativeInfo.class));
    }

    public List<OperativeInfo> getOperativeInfoList() {
        return OperativeInfoList;
    }

    public void setOperativeInfoList(List<OperativeInfo> operativeInfoList) {
        OperativeInfoList = operativeInfoList;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Date getAudit_time() {
        return audit_time;
    }

    public void setAudit_time(Date audit_time) {
        this.audit_time = audit_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public CompanyProd getCompanyProd() {
        return companyProd;
    }

    public void setCompanyProd(CompanyProd companyProd) {
        this.companyProd = companyProd;
    }

    public CompanyInformation getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(CompanyInformation companyInfo) {
        this.companyInfo = companyInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyProdDetail that = (CompanyProdDetail) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (proc_id != null ? !proc_id.equals(that.proc_id) : that.proc_id != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (cp_type != null ? !cp_type.equals(that.cp_type) : that.cp_type != null) return false;
        if (org_id != null ? !org_id.equals(that.org_id) : that.org_id != null) return false;
        if (proc_name != null ? !proc_name.equals(that.proc_name) : that.proc_name != null) return false;
        if (logo_path != null ? !logo_path.equals(that.logo_path) : that.logo_path != null) return false;
        if (official_url != null ? !official_url.equals(that.official_url) : that.official_url != null) return false;
        if (proc_flag != null ? !proc_flag.equals(that.proc_flag) : that.proc_flag != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) return false;
        if (audit_person != null ? !audit_person.equals(that.audit_person) : that.audit_person != null) return false;
        if (audit_state != null ? !audit_state.equals(that.audit_state) : that.audit_state != null) return false;
        if (audit_message != null ? !audit_message.equals(that.audit_message) : that.audit_message != null)
            return false;
        if (proc_info != null ? !proc_info.equals(that.proc_info) : that.proc_info != null) return false;
        if (operativeInfos != null ? !operativeInfos.equals(that.operativeInfos) : that.operativeInfos != null)
            return false;
        if (OperativeInfoList != null ? !OperativeInfoList.equals(that.OperativeInfoList) : that.OperativeInfoList != null)
            return false;
        if (companyProd != null ? !companyProd.equals(that.companyProd) : that.companyProd != null) return false;
        if (companyInfo != null ? !companyInfo.equals(that.companyInfo) : that.companyInfo != null) return false;
        if (companyAddress != null ? !companyAddress.equals(that.companyAddress) : that.companyAddress != null)
            return false;
        if (registeredCapital != null ? !registeredCapital.equals(that.registeredCapital) : that.registeredCapital != null)
            return false;
        if (introduction != null ? !introduction.equals(that.introduction) : that.introduction != null) return false;
        if (audit_time != null ? !audit_time.equals(that.audit_time) : that.audit_time != null) return false;
        if (create_time != null ? !create_time.equals(that.create_time) : that.create_time != null) return false;
        if (update_time != null ? !update_time.equals(that.update_time) : that.update_time != null) return false;
        if (show_click_num != null ? !show_click_num.equals(that.show_click_num) : that.show_click_num != null)
            return false;
        return flag_msg != null ? flag_msg.equals(that.flag_msg) : that.flag_msg == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (proc_id != null ? proc_id.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (cp_type != null ? cp_type.hashCode() : 0);
        result = 31 * result + (org_id != null ? org_id.hashCode() : 0);
        result = 31 * result + (proc_name != null ? proc_name.hashCode() : 0);
        result = 31 * result + (logo_path != null ? logo_path.hashCode() : 0);
        result = 31 * result + (official_url != null ? official_url.hashCode() : 0);
        result = 31 * result + (proc_flag != null ? proc_flag.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (audit_person != null ? audit_person.hashCode() : 0);
        result = 31 * result + (audit_state != null ? audit_state.hashCode() : 0);
        result = 31 * result + (audit_message != null ? audit_message.hashCode() : 0);
        result = 31 * result + (proc_info != null ? proc_info.hashCode() : 0);
        result = 31 * result + (operativeInfos != null ? operativeInfos.hashCode() : 0);
        result = 31 * result + (OperativeInfoList != null ? OperativeInfoList.hashCode() : 0);
        result = 31 * result + (companyProd != null ? companyProd.hashCode() : 0);
        result = 31 * result + (companyInfo != null ? companyInfo.hashCode() : 0);
        result = 31 * result + (companyAddress != null ? companyAddress.hashCode() : 0);
        result = 31 * result + (registeredCapital != null ? registeredCapital.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (audit_time != null ? audit_time.hashCode() : 0);
        result = 31 * result + (create_time != null ? create_time.hashCode() : 0);
        result = 31 * result + (update_time != null ? update_time.hashCode() : 0);
        result = 31 * result + (show_click_num != null ? show_click_num.hashCode() : 0);
        result = 31 * result + (flag_msg != null ? flag_msg.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyProdDetail{" +
                "id=" + id +
                ", proc_id=" + proc_id +
                ", type=" + type +
                ", cp_type=" + cp_type +
                ", org_id=" + org_id +
                ", proc_name='" + proc_name + '\'' +
                ", logo_path='" + logo_path + '\'' +
                ", official_url='" + official_url + '\'' +
                ", proc_flag=" + proc_flag +
                ", status=" + status +
                ", sort=" + sort +
                ", audit_person='" + audit_person + '\'' +
                ", audit_state=" + audit_state +
                ", audit_message='" + audit_message + '\'' +
                ", proc_info='" + proc_info + '\'' +
                ", operativeInfos='" + operativeInfos + '\'' +
                ", OperativeInfoList=" + OperativeInfoList +
                ", companyProd=" + companyProd +
                ", companyInfo=" + companyInfo +
                ", companyAddress='" + companyAddress + '\'' +
                ", p_message='" + p_message + '\'' +
                ", registeredCapital='" + registeredCapital + '\'' +
                ", introduction='" + introduction + '\'' +
                ", audit_time=" + audit_time +
                ", create_time=" + create_time +
                ", regectMessage=" + regectMessage +
                ", update_time=" + update_time +
                ", show_click_num=" + show_click_num +
                ", flag_msg='" + flag_msg + '\'' +
                '}';
    }
}