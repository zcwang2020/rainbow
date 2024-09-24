package com.white.meta.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: tmind
 * @Date: 2024/9/24 13:21
 * @Description:
 */
public enum UserStatusEnum {

    LOCKED(0, "冻结"),

    NORMAL(1, "正常");

    // 添加此注解，在实体类中可以直接使用枚举类型
    @EnumValue
    private final Integer status;
    // 添加此注解，在序列化时，返回给前端的枚举类型为枚举值
    @JsonValue
    private final String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
