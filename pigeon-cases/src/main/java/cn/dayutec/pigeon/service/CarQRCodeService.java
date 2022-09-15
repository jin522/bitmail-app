package cn.dayutec.pigeon.service;

import cn.dayutec.pigeon.entity.SubmitPara;
import cn.dayutec.pigeon.entity.req.CarInfoReq;
import cn.dayutec.pigeon.entity.resp.CarBaseInfoResp;
import cn.dayutec.pigeon.utils.results.Result;

public interface CarQRCodeService {
    Result<Boolean> parseQrcode(String carToken, SubmitPara submitPara);

    Result<CarBaseInfoResp> getInform(String carToken);

    Result<String> genCarToken(CarInfoReq carInfoReq);
}
