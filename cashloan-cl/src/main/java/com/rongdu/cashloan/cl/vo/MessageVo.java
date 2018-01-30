package com.rongdu.cashloan.cl.vo;

import com.rongdu.cashloan.cl.domain.Message;

import java.util.Date;
import java.util.List;

public class MessageVo {
    private String date;

    private List<Message> list;

    public MessageVo(String date, List<Message> list) {
        this.date = date;
        this.list = list;
    }

    public MessageVo() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Message> getList() {
        return list;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MessageVo{" +
                "date=" + date +
                ", list=" + list +
                '}';
    }
}
