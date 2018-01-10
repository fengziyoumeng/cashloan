package com.rongdu.cashloan.cl.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.mapper.ClFlowInfoMapper;
import com.rongdu.cashloan.cl.vo.ChannelForH5;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.domain.Channel;
import com.rongdu.cashloan.cl.mapper.ChannelMapper;
import com.rongdu.cashloan.cl.model.ChannelCountModel;
import com.rongdu.cashloan.cl.model.ChannelModel;
import com.rongdu.cashloan.cl.service.ChannelService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;

@Service("channelService")
public class ChannelServiceImpl extends BaseServiceImpl<Channel, Long> implements ChannelService {

    @Resource
    private ChannelMapper channelMapper;

    @Resource
    protected MybatisService mybatisService;

    @Resource
    protected ClFlowInfoMapper clFlowInfoMapper;

    @Override
    public BaseMapper<Channel, Long> getMapper() {
        return channelMapper;
    }

    @Override
    public boolean save(Channel channel) {
        channel.setCreateTime(new Date());
        channel.setState(ChannelModel.STATE_ENABLE);
        int result = channelMapper.save(channel);
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Map<String, Object> paramMap) {
        int result = channelMapper.updateSelective(paramMap);
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Long findIdByCode(String code) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", code);
        return channelMapper.findIdByCode(paramMap);
    }

    @Override
    public Channel findByCode(String code) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", code);
        return channelMapper.findSelective(paramMap);
    }

    @Override
    public Page<ChannelModel> page(int current, int pageSize,
                                   Map<String, Object> searchMap) {
        PageHelper.startPage(current, pageSize);
        Page<ChannelModel> page = (Page<ChannelModel>) channelMapper
                .page(searchMap);
        return page;
    }

    @Override
    public Page<ChannelCountModel> channelUserList(int current, int pageSize,
                                                   Map<String, Object> searchMap) {
        PageHelper.startPage(current, pageSize);
        Page<ChannelCountModel> page = (Page<ChannelCountModel>) channelMapper.channelUserList(searchMap);
        return page;
    }

    @Override
    public List<Channel> listChannel() {
        return channelMapper.listChannel();
    }

    /**
     * 统计不同渠道的注册总数,按时间查询
     *
     * @param "channelId"
     * @return
     */
    @Override
    public List<ChannelForH5> registerCountList(Date beginTime, Date endTime) {
        HashMap<String, Object> params = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (beginTime != null & endTime != null) {
            params.put("beginTime", sdf.format(beginTime));
            calendar.setTime(endTime);
            calendar.add(Calendar.DAY_OF_MONTH,1);
            params.put("endTime", sdf.format(calendar.getTime()));
        } else {
            try {
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date _endTime = calendar.getTime();
                String endTimeFormat = sdf.format(_endTime);
                Date begin = sdf.parse("2016-01-01");
                String beginTimeFormat = sdf.format(begin);

                params.put("beginTime", beginTimeFormat);
                params.put("endTime", endTimeFormat);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //注册总数
        List<ChannelForH5> channelForH5List = mybatisService.channelForH5("usr.registerCountByChannelId", params);

        //昨日注册数
        HashMap<String, Object> parm = new HashMap<>();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();

        String _yesterday = sdf.format(yesterday);
        String _today = sdf.format(new Date());
        parm.put("beginTime", _yesterday);
        parm.put("endTime", _today);
        List<ChannelForH5> yesterdayCount = mybatisService.channelForH5("usr.CountYesterdayByChannelId", parm);

        //把昨日注册数合并进去
        for (ChannelForH5 channelForH5 : yesterdayCount) {
            for (ChannelForH5 forH5 : channelForH5List) {
                if (channelForH5.getChannelId().equals(forH5.getChannelId())) {
                    forH5.setYesterdayRegisterCount(channelForH5.getYesterdayRegisterCount());
                }
            }
        }

        //今日注册数
        HashMap<String, Object> todayParam = new HashMap<>();
        calendar.setTime(new Date());
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();
        String todayFormatter = sdf.format(today);
        String tomorrowFormatter = sdf.format(tomorrow);
        todayParam.put("beginTime", todayFormatter);
        todayParam.put("endTime", tomorrowFormatter);

        List<ChannelForH5> todayCount = mybatisService.channelForH5("usr.CountTodayByChannelId", todayParam);
        //把今日注册数合并进去
        for (ChannelForH5 channelForH5 : todayCount) {
            for (ChannelForH5 forH5 : channelForH5List) {
                if (channelForH5.getChannelId().equals(forH5.getChannelId())) {
                    forH5.setTodayRegisterCount(channelForH5.getTodayRegisterCount());
                }
            }
        }

        //把渠道名,编码合并进去
        List<ChannelForH5> list = new ArrayList<>();
        for (ChannelForH5 forH5 : channelForH5List) {
            ChannelForH5 channelForH5 = new ChannelForH5();
            Long id = forH5.getChannelId();
            if (id != null) {
                Channel channel = channelMapper.selectById(id);
                if (channel != null) {
                    channelForH5.setChannelCode(channel.getCode());
                    channelForH5.setChannelName(channel.getName());
                    channelForH5.setChannelId(forH5.getChannelId());
                    channelForH5.setTotalRegisterCount(forH5.getTotalRegisterCount());
                    channelForH5.setYesterdayRegisterCount(forH5.getYesterdayRegisterCount());
                    channelForH5.setTodayRegisterCount(forH5.getTodayRegisterCount());
                    list.add(channelForH5);
                }
            }
        }
        return list;
    }


    /**
     * 指定在2017/12/28日后按80%的量来统计
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<ChannelForH5> registerCountFor80(Date beginTime, Date endTime) {
        HashMap<String, Object> params = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ChannelForH5> channelForH5List = null;
        double ratio = 0.8;
        try {
            //分隔日期是2017-12-28日,写29日是因为查找的是29日之前的,不包含29日
            Date dateEnd2017_12_28 = sdf.parse("2017-12-29");


            if (beginTime != null & endTime != null) {
                calendar.setTime(endTime);
                calendar.add(Calendar.DAY_OF_MONTH,1);
                endTime = calendar.getTime();

                params.put("beginTime", sdf.format(beginTime));
                params.put("endTime", sdf.format(endTime));
                channelForH5List = mybatisService.channelForH5("usr.registerCountByChannelId", params);


                if (dateEnd2017_12_28.before(beginTime)) {
                    //查询的起始日期在2017-12-28之后
                    for (ChannelForH5 channelForH5 : channelForH5List) {
                        channelForH5.setTotalRegisterCount((int) (channelForH5.getTotalRegisterCount().doubleValue() * ratio));
                    }
                } else if (dateEnd2017_12_28.before(endTime) && beginTime.before(dateEnd2017_12_28)) {
                    //2017-12-28在查询的起始和结束日期之间

                    //2017-12-28之后的减去20%
                    params.put("beginTime", sdf.format(dateEnd2017_12_28));
                    params.put("endTime", sdf.format(endTime));
                    List<ChannelForH5> List2 = mybatisService.channelForH5("usr.registerCountByChannelId", params);
                    for (ChannelForH5 channelForH5 : channelForH5List) {
                        for (ChannelForH5 forH5 : List2) {
                            if (channelForH5.getChannelId().equals(forH5.getChannelId())) {
                                channelForH5.setTotalRegisterCount(channelForH5.getTotalRegisterCount() - (int) (forH5.getTotalRegisterCount().doubleValue() * (1.0 - ratio)));
                            }
                        }
                    }
                }

            } else {
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                Date _endTime = calendar.getTime();
                String endTimeFormat = sdf.format(_endTime);
                Date _beginTimeFormat = sdf.parse("2016-01-01");
                String beginTimeFormat = sdf.format(_beginTimeFormat);
                //2016-01-01到现在的数据
                params.put("beginTime", beginTimeFormat);
                params.put("endTime", endTimeFormat);
                channelForH5List = mybatisService.channelForH5("usr.registerCountByChannelId", params);

                params.put("beginTime", dateEnd2017_12_28);
                params.put("endTime", endTimeFormat);
                List<ChannelForH5> List1 = mybatisService.channelForH5("usr.registerCountByChannelId", params);
                for (ChannelForH5 channelForH5 : channelForH5List) {
                    for (ChannelForH5 forH5 : List1) {
                        if(channelForH5.getChannelId().equals(forH5.getChannelId())){
                            channelForH5.setTotalRegisterCount(channelForH5.getTotalRegisterCount() - (int) (forH5.getTotalRegisterCount().doubleValue() * (1.0 - ratio)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //昨日注册数
        HashMap<String, Object> parm = new HashMap<>();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();

        String _yesterday = sdf.format(yesterday);
        String _today = sdf.format(new Date());
        parm.put("beginTime", _yesterday);
        parm.put("endTime", _today);
        List<ChannelForH5> yesterdayCount = mybatisService.channelForH5("usr.CountYesterdayByChannelId", parm);

        //把昨日注册数合并进去
        for (ChannelForH5 channelForH5 : yesterdayCount) {
            for (ChannelForH5 forH5 : channelForH5List) {
                if (channelForH5.getChannelId().equals(forH5.getChannelId())) {
                    forH5.setYesterdayRegisterCount((int) (channelForH5.getYesterdayRegisterCount().doubleValue() * ratio));
                }
            }
        }

        //今日注册数
        HashMap<String, Object> todayParam = new HashMap<>();
        calendar.setTime(new Date());
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = calendar.getTime();
        String todayFormatter = sdf.format(today);
        String tomorrowFormatter = sdf.format(tomorrow);
        todayParam.put("beginTime", todayFormatter);
        todayParam.put("endTime", tomorrowFormatter);

        List<ChannelForH5> todayCount = mybatisService.channelForH5("usr.CountTodayByChannelId", todayParam);
        //把今日注册数合并进去
        for (ChannelForH5 channelForH5 : todayCount) {
            for (ChannelForH5 forH5 : channelForH5List) {
                if (channelForH5.getChannelId().equals(forH5.getChannelId())) {
                    forH5.setTodayRegisterCount((int) (channelForH5.getTodayRegisterCount().doubleValue() * ratio));
                }
            }
        }

        //把渠道名,编码合并进去
        List<ChannelForH5> list = new ArrayList<>();
        for (ChannelForH5 forH5 : channelForH5List) {
            ChannelForH5 channelForH5 = new ChannelForH5();
            Long id = forH5.getChannelId();
            if (id != null) {
                Channel channel = channelMapper.selectById(id);
                if (channel != null) {
                    channelForH5.setChannelCode(channel.getCode());
                    channelForH5.setChannelName(channel.getName());
                    channelForH5.setChannelId(forH5.getChannelId());
                    channelForH5.setTotalRegisterCount(forH5.getTotalRegisterCount());
                    channelForH5.setYesterdayRegisterCount(forH5.getYesterdayRegisterCount());
                    channelForH5.setTodayRegisterCount(forH5.getTodayRegisterCount());
                    list.add(channelForH5);
                }
            }
        }
        return list;
    }

    @Override
    public List<Channel> listChannelWithoutApp() {
        return channelMapper.listChannelWithoutApp();
    }

}