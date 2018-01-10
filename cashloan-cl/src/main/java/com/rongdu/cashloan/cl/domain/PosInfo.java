package com.rongdu.cashloan.cl.domain;

public class PosInfo {

    private Long id;

    private String posPlatName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosPlatName() {
        return posPlatName;
    }

    public void setPosPlatName(String posPlatName) {
        this.posPlatName = posPlatName;
    }

    @Override
    public String toString() {
        return "PosInfo{" +
                "id=" + id +
                ", posPlatName='" + posPlatName + '\'' +
                '}';
    }
}
