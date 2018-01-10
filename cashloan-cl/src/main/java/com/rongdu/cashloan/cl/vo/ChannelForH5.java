package com.rongdu.cashloan.cl.vo;

public class ChannelForH5 {
    private Long channelId;
    private String channelCode;
    private String channelName;
    private Integer todayRegisterCount = 0;
    private Integer yesterdayRegisterCount = 0;
    private Integer thisMonthRegisterCount;
    private Integer preMonthRegisterCount;
    private Integer totalRegisterCount;


    public Integer getYesterdayRegisterCount() {
        return yesterdayRegisterCount;
    }

    public void setYesterdayRegisterCount(Integer yesterdayRegisterCount) {
        this.yesterdayRegisterCount = yesterdayRegisterCount;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getTodayRegisterCount() {
        return todayRegisterCount;
    }

    public void setTodayRegisterCount(Integer todayRegisterCount) {
        this.todayRegisterCount = todayRegisterCount;
    }

    public Integer getThisMonthRegisterCount() {
        return thisMonthRegisterCount;
    }

    public void setThisMonthRegisterCount(Integer thisMonthRegisterCount) {
        this.thisMonthRegisterCount = thisMonthRegisterCount;
    }

    public Integer getPreMonthRegisterCount() {
        return preMonthRegisterCount;
    }

    public void setPreMonthRegisterCount(Integer preMonthRegisterCount) {
        this.preMonthRegisterCount = preMonthRegisterCount;
    }

    public Integer getTotalRegisterCount() {
        return totalRegisterCount;
    }

    public void setTotalRegisterCount(Integer totalRegisterCount) {
        this.totalRegisterCount = totalRegisterCount;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "ChannelForH5{" +
                "channelId=" + channelId +
                ", channelCode='" + channelCode + '\'' +
                ", channelName='" + channelName + '\'' +
                ", todayRegisterCount=" + todayRegisterCount +
                ", thisMonthRegisterCount=" + thisMonthRegisterCount +
                ", preMonthRegisterCount=" + preMonthRegisterCount +
                ", totalRegisterCount=" + totalRegisterCount +
                '}';
    }
}
