package com.white.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: tmind
 * @Date: 2024/9/10 10:54
 * @Description:
 */
@Data
public class User {

    /**
     * id自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 详细信息
     */
    private String info;

    /**
     * 使用状态，1正常，0冻结
     */
    private int status = 0;

    /**
     * Balance of the user
     */
    private int balance = 0;

    /**
     * Timestamp when the user was created
     */
    private LocalDateTime createTime;

    /**
     * Timestamp when the user was last updated
     */
    private LocalDateTime updateTime;

    @TableField("`count`")
    private Long count;

    @TableField(exist = false)
    private String show;
}