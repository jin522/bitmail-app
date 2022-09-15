package cn.dayutec.pigeon.controller;

import cn.dayutec.pigeon.entity.SubmitPara;
import cn.dayutec.pigeon.entity.req.CarInfoReq;
import cn.dayutec.pigeon.entity.resp.CarBaseInfoResp;
import cn.dayutec.pigeon.service.CarQRCodeService;
import cn.dayutec.pigeon.utils.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/car")
public class CarQRCodeController {

    @Autowired
    CarQRCodeService carQRCodeService;

    /**
     * 生成挪车二维码
     */
    @PostMapping("genCarToken")
    private Result<String> genCarToken(@RequestBody CarInfoReq carInfoReq) {
        return carQRCodeService.genCarToken(carInfoReq);
    }

    /**
     * 发送消息给车主通知挪车
     */
    @PostMapping("inform")
    private Result<Boolean> parseQrcode(String carToken, @RequestBody SubmitPara submitPara) {
        return carQRCodeService.parseQrcode(carToken, submitPara);
    }

    /**
     * 判断是否请求间隔内重复发送请求
     */
    @GetMapping("getInform")
    private Result<CarBaseInfoResp> getInform(String carToken) {
        return carQRCodeService.getInform(carToken);
    }
}
