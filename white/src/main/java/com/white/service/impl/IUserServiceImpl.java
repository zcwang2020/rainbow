package com.white.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.mapper.UserMapper;
import com.white.po.User;
import com.white.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Author: tmind
 * @Date: 2024/9/19 15:13
 * @Description:
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
