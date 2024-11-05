package com.white.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.white.entity.Location;
import com.white.mapper.LocationMapper;
import com.white.service.ILocationService;
import org.springframework.stereotype.Service;

/**
 * @Author: tmind
 * @Date: 2024/11/5 12:50
 * @Description:
 */
@Service
public class LocationServiceImpl extends ServiceImpl<LocationMapper, Location>  implements ILocationService {
}
