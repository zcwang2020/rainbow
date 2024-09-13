package com.white.meta.enums;

public enum JstCode {
    SUCCESS(0,"执行成功", "执行成功"),

    SIGN_FAIL(10,"验证失败!无效签名", "签名错误，请参考签名文档"),

    ACCESS_OVERTIME(100, "access_token超时", "商家自有应用授权/服务商应用授权"),

    IP_FORBIDDEN(110, "验证失败!已设置IP白名单，验证IP无效：xx.xxx.xx.xxx，请联系管理员添加IP", "ip无效，请登陆开放平台后台应用-详情-IP白名单，添加"),

    PARAM_BLANK(130, "传输数据不能为空", "传输数据错误，请自行根据返回数据检查入参类型/数据，如无法解决请提交工单咨询相关技术"),

    PARAM_(140,	"参数不符合规范,请检查传入参数!", "传递参数有误,请自行根据返回数据检查入参类型/数据，如无法解决请提交工单咨询相关技术"),

    BIZ_EXCEPTION(150,	"内部处理异常！", "内部处理数据出错，请自行根据返回数据检查入参类型/数据，如无法解决请提交工单咨询相关技术"),

    DELIVER_NO_NOT_EXIST(160,	"调出仓和调入仓，不能是同一个/指定快递单xxxx的单据不存在", "内部保存失败，请自行根据返回数据检查入参类型/数据，如无法解决请提交工单咨询相关技术"),

    SHOP_NOT_EXIST(170,	"店铺编号[xxxxx]不存在!", "验证数据完整性失败，请自行根据返回数据检查入参类型/数据，如无法解决请提交工单咨询相关技术"),

    TIMESTAMP_OVERTIME(180,	"执行失败!", "timestamp无效，API服务端允许客户端请求最大时间误差为10分钟。"),

    CHECK_FAIL(190,	"验证失败!", "调用无权限，请登录右上角控制台，在应用详情- API权限申请页面申请对应接口权限"),

    REQUEST_LIMIT(199,	"调用太频繁，请稍后再试!", "调用接口并发量过高，请查看API权限包调用并发限制"),

    REQUEST_TIMES_LIMIT(200,	"调用频次超过限制!",	"调用接口频率过高，请查看API权限包调用频次限制");
     
    private int code;

    private String msg;

    private String desc;

    JstCode(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDesc() {
        return desc;
    }
}
