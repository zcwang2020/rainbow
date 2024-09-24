package com.white;

import com.alibaba.fastjson.JSON;
import com.white.meta.enums.UserStatusEnum;
import com.white.po.User;
import com.white.po.UserJsonInfo;
import com.white.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Test
    void batchSaveOneByOne() {
        // 计算耗时
        long start = System.currentTimeMillis();
        // 1. 循环100000次,耗时： 306798ms
        /*for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setUsername("张三" + i);
            user.setPassword("123456");
            user.setPhone("13800138000");
            UserJsonInfo userJsonInfo = new UserJsonInfo();
            userJsonInfo.setAge(i);
            userJsonInfo.setCity("北京");
            user.setInfo(userJsonInfo);
            user.setStatus(UserStatusEnum.NORMAL);
            user.setBalance(i);
            user.setDeleted(0);
            // 当前时间
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            userService.save(user);
        }*/
        // 2. 循环100000次, 分批每1000条插入一次，耗时： 18673ms
        /*List<User> users = new ArrayList<>(1000);
        for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setUsername("张三" + i);
            user.setPassword("123456");
            user.setPhone("13800138000");
            UserJsonInfo userJsonInfo = new UserJsonInfo();
            userJsonInfo.setAge(i);
            userJsonInfo.setCity("北京");
            user.setInfo(userJsonInfo);
            user.setStatus(UserStatusEnum.NORMAL);
            user.setBalance(i);
            user.setDeleted(0);
            // 当前时间
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            users.add(user);
            // 每1000条插入一次
            if ((i + 1) % 1000 == 0) {
                userService.saveBatch(users);
                // 清空已插入的用户列表
                users.clear();
            }
        }
        // 插入剩余的用户
        if (!users.isEmpty()) {
            userService.saveBatch(users);
        }*/
        // 3. 循环100000次, 一次性插入所有数据，&rewriteBatchedStatements=true，耗时： 8330ms
        List<User> users = new ArrayList<>(100000);
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("张三" + i);
            user.setPassword("123456");
            user.setPhone("13800138000");
            UserJsonInfo userJsonInfo = new UserJsonInfo();
            userJsonInfo.setAge(i);
            userJsonInfo.setCity("北京");
            user.setInfo(userJsonInfo);
            user.setStatus(UserStatusEnum.NORMAL);
            user.setBalance(i);
            user.setDeleted(0);
            // 当前时间
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            users.add(user);
        }
        userService.saveBatch(users);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }
}
