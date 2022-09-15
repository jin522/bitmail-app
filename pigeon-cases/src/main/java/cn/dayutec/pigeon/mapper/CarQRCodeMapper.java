package cn.dayutec.pigeon.mapper;


import cn.dayutec.pigeon.entity.InformInfo;

public interface CarQRCodeMapper {

    InformInfo selectInformByCarToken(String carToken);

    int updateInformCountByToken(String carToken, long newInformTime);

    int updateInitInformCountByToken(String carToken, long newInformTime);

    int insertOrUpdateCarToken(String carToken, String pushToken, String licence,String userId);
}
