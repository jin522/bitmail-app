package cn.dayutec.pigeon.entity.req;

import lombok.Data;

@Data
public class CarInfoReq {
    private String pushToken;
    private String licence;
    private String channelType;
}
