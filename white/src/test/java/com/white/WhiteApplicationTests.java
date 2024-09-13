package com.white;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.white.mapper.UserMapper;
import com.white.meta.enums.JstInterfaceEnum;
import com.white.meta.request.InventoryRequest;
import com.white.meta.request.InventoryResponse;
import com.white.meta.request.JstRequest;
import com.white.meta.utils.OKHttpUtil;
import com.white.po.User;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.object.SqlUpdate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WhiteApplicationTests {

    // @Resource
    // private UserMapper userMapper;
    //
    // @Test
    // void selectList() {
    //     userMapper.selectList(null).forEach(System.out::println);
    // }
    //
    // @Test
    // void insert() {
    //     User user = new User();
    //     user.setId(0L);
    //     user.setUsername("");
    //     user.setPassword("");
    //     user.setPhone("");
    //     user.setInfo("");
    //     user.setStatus(0);
    //     user.setBalance(0);
    //     user.setCreateTime(LocalDateTime.now());
    //     user.setUpdateTime(LocalDateTime.now());
    //
    //     userMapper.insert(user);
    //
    //     System.out.println("user = " + user);
    // }

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
}
