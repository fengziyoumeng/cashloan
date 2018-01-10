package com.rongdu.cashloan.cl.mapper;

import com.rongdu.cashloan.cl.domain.ProblemGeneral;
import com.rongdu.cashloan.cl.domain.ProblemGeneralDetail;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

import java.util.List;

@RDBatisDao
public interface IProblemGeneralMapper extends BaseMapper<ProblemGeneral,Long> {

    /**
     * 查询出所有常见问题
     * @auth wubin
     * @return
     */
    List<ProblemGeneral> getListProblemGeneral();

    List<ProblemGeneralDetail> getListProblemGeneralDetail(int sort);
}
