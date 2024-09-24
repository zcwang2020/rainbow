package com.white.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.white.entity.Address;
import com.white.meta.enums.UserStatusEnum;
import com.white.po.UserJsonInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: tmind
 * @Date: 2024/9/19 15:30
 * @Description:
 */
@Data
public class UserVO {

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
    private UserJsonInfo info;

    /**
     * 使用状态，1正常，0冻结
     */
    private UserStatusEnum status;

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

    private List<Address> addressList;

}
