package com.white.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.white.po.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: tmind
 * @Date: 2024/9/10 11:03
 * @Description:
 */
public interface UserMapper extends BaseMapper<User> {

    void updateCountByIds(@Param(Constants.WRAPPER) LambdaQueryWrapper<User> lambdaQueryWrapper, @Param("count") long count);
}
