package com.rongdu.cashloan.core.mapper;


import com.rongdu.cashloan.core.common.mapper.RDBatisDao;
import com.rongdu.cashloan.core.domain.UserItemEntity;

@RDBatisDao
public interface RDBUserItemMapper {

    int insertOne(UserItemEntity userItem);

    UserItemEntity selectByPk(int id);

    UserItemEntity selectJoin(int id);
}
