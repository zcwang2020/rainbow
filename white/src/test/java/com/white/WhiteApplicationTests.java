package com.white;

import com.white.mapper.UserMapper;
import com.white.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class WhiteApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectList() {
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    void insert() {
        User user = new User();
        user.setId(0L);
        user.setUsername("");
        user.setPassword("");
        user.setPhone("");
        user.setInfo("");
        user.setStatus(0);
        user.setBalance(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userMapper.insert(user);

        System.out.println("user = " + user);
    }

}
