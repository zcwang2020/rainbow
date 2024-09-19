package com.white.controller;

import com.alibaba.fastjson.JSON;
import com.white.po.User;
import com.white.request.UserRequest;
import com.white.service.IUserService;
import com.white.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserController {

    private final IUserService userService;

    @RequestMapping("")
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

    // 增删改查，增加count
    @RequestMapping("/add")
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

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.removeById(id);
        return "success";
    }

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

    @RequestMapping("/detail/{id}")
    public UserVO detail(@PathVariable Long id) {
        User user = userService.getById(id);
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
    }


}
