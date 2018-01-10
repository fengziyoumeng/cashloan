package com.rongdu.cashloan.cl.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageResult implements Serializable{

    private List<?> listDate;
    private Long total;
    private Integer totalPage;
    private Integer currentPage;
    private Integer PageSize;

    public static final PageResult emptyResult = new PageResult(Collections.EMPTY_LIST,0L,1,1);


    public PageResult(List<?> listDate, Long total,Integer currentPage, Integer pageSize) {
        this.listDate = listDate;
        this.total = total;
        this.PageSize = pageSize;
        this.totalPage = (int)(total % pageSize.longValue() == 0 ? total / pageSize.longValue() : (total / pageSize.longValue()+1));
        this.currentPage = currentPage<1 ? 1:(currentPage>this.totalPage? this.totalPage : currentPage);
    }

    public PageResult() {
    }

    public List<?> getListDate() {
        return listDate;
    }

    public void setListDate(List<?> listDate) {
        this.listDate = listDate;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public static PageResult getEmptyResult() {
        return emptyResult;
    }


    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "listDate=" + listDate +
                ", total=" + total +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", PageSize=" + PageSize +
                '}';
    }
}
