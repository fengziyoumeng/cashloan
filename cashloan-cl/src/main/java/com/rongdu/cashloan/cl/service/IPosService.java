package com.rongdu.cashloan.cl.service;


import com.rongdu.cashloan.cl.domain.PosInfo;
import com.rongdu.cashloan.core.common.service.BaseService;

import java.util.List;

public interface IPosService extends BaseService<PosInfo, Long> {

    List<PosInfo> listPosInfo();
}
