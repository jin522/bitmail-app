package cn.dayutec.pigeon.utils.results;

import com.fasterxml.jackson.annotation.JsonValue;


public enum CodeNumEnum {
    //操作成功
    SUCCESS(0),
    //操作成功
    FINISH(3),
    //操作失败
    FAIL(-1),
    //操作可能存在问题，需进行提示
    WARN(1),
    //操作可能存在问题，需进行提示
    ERROR(2),
    // token认证错误码
    TOKEN_ERROR(100),
    //多点登录错误
    MULTI_LOGIN_ERROR(200),
    //权限错误
    ACCESS_ERROR(300),
    //参数异常
    PARAM_ERROR(400),
    //服务端异常
    SERVER_ERROR(500);

    private final int code;

    CodeNumEnum(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "CodeNumEnum{" +
                "code=" + code +
                '}';
    }
}
