package com.white.po;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author: tmind
 * @Date: 2024/9/14 16:04
 * @Description:
 */
@TableName("baomidouuser")
public class BaomidouUser {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
