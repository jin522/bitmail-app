package cn.dayutec.pigeon.entity;

import lombok.Data;

@Data
public class InformInfo {
    private String pushToken;
    private long informTime;
    private int informCount;
    private String licenceNum;
}
