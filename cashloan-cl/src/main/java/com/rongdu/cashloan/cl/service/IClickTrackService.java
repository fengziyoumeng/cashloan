package com.rongdu.cashloan.cl.service;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.ClickTrack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface IClickTrackService {

    int deleteById(long id);

    int save(ClickTrack clickTrack);

    void saveInRedis(ClickTrack clickTrack);

    Page<ClickTrack> queryTrailRecodes(Map<String, Object> params, int currentPage, int pageSize) throws Exception;

    List<ClickTrack> getTrackExcel(String beginTime, String endTime, String userId, String channelName)throws Exception;

    List<ClickTrack> makeTrackExcel(String beginTime, String endTime, String userId, String channelName)throws Exception;

    Map<String,Object> channelSurvey(Map<String,Object> map)throws Exception;

}
