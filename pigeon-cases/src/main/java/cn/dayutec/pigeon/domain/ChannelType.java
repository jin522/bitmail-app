package cn.dayutec.pigeon.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public enum ChannelType {
    // 企业微信应用
    WECHAT_APP("企微应用"),
    // 企业微信群
    WECHAT_GROUP("企微群"),
    // 钉钉群
    DINGTALK_GROUP("钉钉群"),
    // 飞书群
    FEISHU_GROUP("飞书群"),
    // 电子邮件
    E_MAIL("邮件");

    private final String cnName;

    ChannelType(String cnName) {
        this.cnName = cnName;
    }

    public static ChannelType getByCode(int code) {
        for (ChannelType e : ChannelType.values()) {
            if (e.ordinal() == code) {
                return e;
            }
        }
        return null;
    }
}
