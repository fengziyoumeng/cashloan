package com.rongdu.cashloan.cl.domain;

public class Transverter {
    private Long id;

    private Integer switchCode;

    private String  channel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSwitchCode() {
        return switchCode;
    }

    public void setSwitchCode(Integer switchCode) {
        this.switchCode = switchCode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
