package com.white.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: tmind
 * @Date: 2024/9/19 15:33
 * @Description:
 */
@Data
public class UserRequest {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private String info;

    private int status;

    private int balance;

    private Long count;

}
