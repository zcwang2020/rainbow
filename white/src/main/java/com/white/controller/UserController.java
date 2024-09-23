package com.white.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.white.entity.Address;
import com.white.po.User;
import com.white.request.UserRequest;
import com.white.service.IAddressService;
import com.white.service.IUserService;
import com.white.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: tmind
 * @Date: 2024/9/19 15:28
 * @Description:
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "用户管理")
public class UserController {

    private final IUserService userService;

    private final IAddressService addressService;

    @Operation(summary = "列表", description = "列表4864865645")
    @GetMapping("")
    public List<UserVO> list() {
        // 查询转UserVO
        return userService.list().stream().map(user -> {
            UserVO userVO = new UserVO();
            userVO.setId(user.getId());
            userVO.setUsername(user.getUsername());
            userVO.setPassword(user.getPassword());
            userVO.setPhone(user.getPhone());
            userVO.setInfo(user.getInfo());
            userVO.setStatus(user.getStatus());
            userVO.setBalance(user.getBalance());
            userVO.setCount(user.getCount());
            userVO.setCreateTime(user.getCreateTime());
            userVO.setUpdateTime(user.getUpdateTime());
            userVO.setShow(userVO.getUsername() + ":" + userVO.getPassword());
            return userVO;
        }).collect(Collectors.toList());
    }

    @Operation(summary = "添加", description = "添加12154234354")
    @PostMapping("/add")
    public String add(@RequestBody UserRequest request) {
        // request 转 User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setInfo(JSON.toJSONString(request.getInfo()));
        user.setStatus(request.getStatus());
        user.setBalance(request.getBalance());
        // 当前时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setCount(request.getCount());
        userService.save(user);

        return "success";
    }

    @Operation(summary = "删除", description = "删除5644686446")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.removeById(id);
        return "success";
    }

    @Operation(summary = "修改", description = "修改154645534453")
    @PostMapping("/update")
    public String update(@RequestBody UserRequest request) {
        User user = new User();
        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setPhone(request.getPhone());
        user.setInfo(JSON.toJSONString(request.getInfo()));
        user.setStatus(request.getStatus());
        user.setBalance(request.getBalance());
        // 当前时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setCount(request.getCount());
        userService.updateById(user);
        return "success";
    }

    @Operation(summary = "详情", description = "详情56454685534564543")
    @GetMapping("/detail/{id}")
    public UserVO detail(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        List<Address> list = Db.lambdaQuery(Address.class).eq(Address::getUserId, user.getId()).list();
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setPassword(user.getPassword());
        userVO.setPhone(user.getPhone());
        userVO.setInfo(user.getInfo());
        userVO.setStatus(user.getStatus());
        userVO.setBalance(user.getBalance());
        userVO.setCount(user.getCount());
        userVO.setCreateTime(user.getCreateTime());
        userVO.setUpdateTime(user.getUpdateTime());
        userVO.setShow(userVO.getUsername() + ":" + userVO.getPassword());
        userVO.setAddressList(list);
        return userVO;
    }


}
