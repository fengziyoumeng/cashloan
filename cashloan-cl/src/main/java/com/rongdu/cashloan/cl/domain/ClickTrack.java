package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 点击轨迹实体类
 */
public class ClickTrack  implements Serializable {

    private Long id;
    /**
     * 点击的用户
     */
    private Long userId;
    /**
     * 点击的位置(详情:platDetail
                立即申请:platRegister
                信用卡申请:card
                分类图标sortPic
                贷款选项卡loansTabs
                信用卡选项卡creditTabs)
     */
    private String positionMark;
    /**
     * 点击的产品的id
     */
    private Long flag;
    /**
     * 点击时间
     */
    private Date clickTime;

    private String name;

    private String channelName;

    private String recodeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPositionMark() {
        return positionMark;
    }

    public void setPositionMark(String positionMark) {
        this.positionMark = positionMark;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getRecodeDate() {
        return recodeDate;
    }

    public void setRecodeDate(String recodeDate) {
        this.recodeDate = recodeDate;
    }

    @Override
    public String toString() {
        return "ClickTrack{" +
                "id=" + id +
                ", userId=" + userId +
                ", positionMark='" + positionMark + '\'' +
                ", flag=" + flag +
                ", clickTime=" + clickTime +
                ", name='" + name + '\'' +
                ", channelName='" + channelName + '\'' +
                ", recodeDate='" + recodeDate + '\'' +
                '}';
    }
}
