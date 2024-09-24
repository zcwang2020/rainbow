package com.white.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.white.entity.Address;
import com.white.po.User;
import com.white.request.UserRequest;
import com.white.service.IUserService;
import com.white.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "UserController", description = "用户管理1")
public class UserController {

    private final IUserService userService;

    @Operation(summary = "列表", description = "列表")
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

    @Operation(summary = "添加", description = "添加")
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

    @Operation(summary = "删除", description = "删除")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.removeById(id);
        return "success";
    }

    @Operation(summary = "修改", description = "修改")
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

    @Operation(summary = "详情", description = "根据id查询用户及地址详情",
            parameters = {@Parameter(name = "id", description = "用户ID")})
    @GetMapping("/detail")
    public UserVO detail(@RequestParam Long id) {
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
