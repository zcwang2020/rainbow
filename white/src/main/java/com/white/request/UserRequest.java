package com.white.request;

import com.white.meta.enums.UserStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: tmind
 * @Date: 2024/9/19 15:33
 * @Description:
 */
@Data
@Schema(name = "UserRequest", description = "用户请求对象")
public class UserRequest {

    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "用户名", example = "张三")
    private String username;

    @Schema(description = "密码", example = "password123")
    private String password;

    @Schema(description = "电话号码", example = "13800138000")
    private String phone;

    @Schema(description = "个人信息", example = "喜欢编程")
    private String info;

    @Schema(description = "状态", example = "1")
    private UserStatusEnum status;

    @Schema(description = "余额", example = "1000")
    private int balance;

    @Schema(description = "计数器", example = "50")
    private Long count;

}
