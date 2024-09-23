package com.white;

import com.alibaba.fastjson.JSON;
import com.white.po.User;
import com.white.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: tmind
 * @Date: 2024/9/19 15:22
 * @Description:
 */
@SpringBootTest
public class MpServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    void test() {
        List<User> list = userService.list();
        System.out.println("list = " + JSON.toJSONString(list));
    }

    void queryUserAndAddress() {

    }
}
