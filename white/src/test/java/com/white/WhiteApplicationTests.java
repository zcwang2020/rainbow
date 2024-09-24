package com.white;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.white.mapper.UserMapper;
import com.white.meta.enums.JstInterfaceEnum;
import com.white.meta.enums.UserStatusEnum;
import com.white.meta.request.InventoryRequest;
import com.white.meta.request.InventoryResponse;
import com.white.meta.request.JstRequest;
import com.white.meta.utils.OKHttpUtil;
import com.white.po.User;
import com.white.po.UserJsonInfo;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class WhiteApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 更新传入值，mp组装复杂sql
     */
    @Test
    void update() {
        List<Long> ids = List.of(1L, 2L);
        long count = 20;
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(User::getId, ids);

        userMapper.updateCountByIds(lambdaQueryWrapper, count);
    }

    @Test                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    void selectList() {
        List<User> baomidouUsers = userMapper.selectList(null);
        System.out.println("users = " + JSON.toJSONString(baomidouUsers));

        // mybatisplus   status字段求和
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("sum(count) as countSum");
        // username = user1
        queryWrapper.eq("username", "user1");
        Map<String, Object> result = userMapper.selectMaps(queryWrapper).get(0);
        System.out.println("result = " + JSON.toJSONString(result));
        // 判断是否为空
        if (!CollectionUtils.isEmpty(result)) {
            BigDecimal countSum = (BigDecimal) result.get("countSum");
            System.out.println("countSum = " + countSum);
        }

    }

    @Test
    void insert() {
        User user = new User();
        user.setUsername("tmind");
        user.setPassword("123456");
        user.setPhone("12345678901");
        user.setInfo(new UserJsonInfo());
        user.setStatus(UserStatusEnum.LOCKED);
        user.setBalance(0);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }


    /**
     * 聚水潭库存查询
     */
    @Test
    void inventoryQuery() {
        JstInterfaceEnum type = JstInterfaceEnum.INVENTORY_QUERY;
        int index = 1, limit = 100;
        InventoryRequest request = new InventoryRequest();
        request.setModified_begin("2024-09-06 00:00:00");
        request.setModified_end("2024-09-07 00:00:00");
        List<InventoryResponse.InventoryData> all = Lists.newArrayList();
        while(true){
            request.setPage_index(index).setPage_size(limit);

            JstRequest param = new JstRequest().setBiz(request);
            Map<String, String> paramMap = param.toMap();
            String res = OKHttpUtil.jstPost(type, paramMap);
            if(res == null){
                return;
            }

            InventoryResponse inventory = JSON.parseObject(res, InventoryResponse.class);
            List<InventoryResponse.InventoryData> datas = inventory.getInventorys();
            datas.forEach(data -> {
                if(!(StringUtils.isBlank(data.getI_id()) && StringUtils.isBlank(data.getSku_id()))){
                    all.add(data);
                }
            });

            if(!inventory.getHas_next()){
                break;
            }
            index++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("all = " + JSON.toJSONString(all));
    }

    /**
     * 随机的16位UUID字符串，并写入文件
     */
    @Test
    void export() {
        // 生成20w条数据，格式为https://m.you.163.com/spark/index?appConfig=1_1_1&xhh=XXX字符串，并打印到指定文件
        String urlTemplate = "https://m.you.163.com/spark/index?appConfig=1_1_1&xhh=";
        String filePath = "D:\\test.txt";
        int count = 60000;

        try (FileWriter writer = new FileWriter(filePath, false)) { // 以覆盖模式打开文件
            for (int i = 0; i < count; i++) {
                // 生成随机的16位UUID字符串
                UUID uuid = UUID.randomUUID();
                String xhh = uuid.toString().replace("-", "").substring(0, 16);
                String newUrl = urlTemplate + xhh;

                writer.write(newUrl + "\n"); // 直接写入文件

                if (i % 10000 == 0) {
                    System.out.println("已生成" + i + "条数据");
                }
            }
        } catch (IOException e) {
            // 更好地处理异常，例如记录日志
            System.err.println("文件写入错误: " + e.getMessage());
        }

        System.out.println("生成完毕");
    }


}
