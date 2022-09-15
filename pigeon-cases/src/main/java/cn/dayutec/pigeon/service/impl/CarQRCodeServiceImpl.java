package cn.dayutec.pigeon.service.impl;

import cn.dayutec.pigeon.Constants;
import cn.dayutec.pigeon.entity.InformInfo;
import cn.dayutec.pigeon.entity.SubmitPara;
import cn.dayutec.pigeon.entity.req.CarInfoReq;
import cn.dayutec.pigeon.entity.resp.CarBaseInfoResp;
import cn.dayutec.pigeon.mapper.CarQRCodeMapper;
import cn.dayutec.pigeon.service.CarQRCodeService;
import cn.dayutec.pigeon.utils.HttpClientUtil;
import cn.dayutec.pigeon.utils.PlatformConfUtil;
import cn.dayutec.pigeon.utils.TimeTransfUtil;
import cn.dayutec.pigeon.utils.results.Result;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class CarQRCodeServiceImpl implements CarQRCodeService {
    private static final Logger LOG = LoggerFactory.getLogger(CarQRCodeServiceImpl.class);

    static String rootUrl = PlatformConfUtil.getString(Constants.BITMAIL_API_ROOT_REQUEST_URL);
    @Autowired
    CarQRCodeMapper carQRCodeMapper;

    @Override
    public Result<Boolean> parseQrcode(String carToken, SubmitPara submitPara) {
        long newInformTime = System.currentTimeMillis();
        InformInfo informInfo = carQRCodeMapper.selectInformByCarToken(carToken);
        String todayDate = TimeTransfUtil.getNowDateString();
        String informDate = TimeTransfUtil.long2StringTime(informInfo.getInformTime());
        int informCount = PlatformConfUtil.getInt(Constants.CASE_MOVECAR_ALLOW_INFORM_MAX_COUNT_EVERYDAY);
        // 检查是否是新的一天，是否重置日限额数据
        if (todayDate.equals(informDate)) {
            // 检查是否达到日通知条数上限
            if (informInfo.getInformCount() >= informCount) {
                return Result.fail("通知车主失败，今日通知次数已达上限！", false);
            } else {
                //检查是否符合通知时间间隔;
                // 单位s
                int limitIntervalTime = PlatformConfUtil.getInt(Constants.CASE_MOVECAR_ALLOW_INFORM_TIME_INTERVAL_EVERYDAY);
                long actualIntervalTime = (System.currentTimeMillis() - informInfo.getInformTime()) / 1000;
                if (limitIntervalTime > actualIntervalTime) {
                    return Result.fail("消息通知过于频繁，请稍后再试！", false);
                }
                carQRCodeMapper.updateInformCountByToken(carToken, newInformTime);
            }
        } else {
            carQRCodeMapper.updateInitInformCountByToken(carToken, newInformTime);
        }

        // CAR_CODE通知模板
        String jsonModel = null;
        String[] ptPart = informInfo.getPushToken().split("-");
        if (ptPart.length == 2) {
            int channelCode = Integer.parseInt(ptPart[1]);
            String channelName = getChannelName(channelCode);
            switch (channelName) {
                case "wechat_app": // 企微应用
                    jsonModel = PlatformConfUtil.getString(Constants.CASE_MSG_CARCODE_CHANNEL_WXAPP);
                    break;
                case "wechat_group": // 企微群
                    jsonModel = PlatformConfUtil.getString(Constants.CASE_MSG_CARCODE_CHANNEL_WXGROUP);
                    break;
                case "dingtalk_group": // 钉钉群
                    jsonModel = PlatformConfUtil.getString(Constants.CASE_MSG_CARCODE_CHANNEL_DINGTALKGROUP);
                    break;
                case "feishu_group": // 飞书群
                    jsonModel = PlatformConfUtil.getString(Constants.CASE_MSG_CARCODE_CHANNEL_FEISHUGROUP);
                    break;
            }
        }

        //消息拼装
        String text;
        if ("".equals(submitPara.getPhoneNum())) {
            String textModel = PlatformConfUtil.getString(Constants.CASE_MSG_CARCODE_TEMPLATE);
            text = String.format(textModel, informInfo.getLicenceNum(), submitPara.getReason());
        } else {
            String textModel = PlatformConfUtil.getString(Constants.CASE_MSG_CARCODE_PHONE_TEMPLATE);
            text = String.format(textModel, informInfo.getLicenceNum(), submitPara.getReason(), submitPara.getPhoneNum());
        }
        // 如果用户选择自定义模板，则替换模板
        if (null != jsonModel) {
            JSONObject jsonMess = JSONObject.parseObject(text);
            String title = jsonMess.getString("title");
            String content = jsonMess.getString("content");
            text = jsonModel.replace("&bm1;", title).replace("&bm2;", content);
            jsonMess.put("title","");
            jsonMess.put("content",text);
            text = jsonMess.toString();
        }

        // 发消息
        String pushUrl = rootUrl+ "/bit/app-send?pushToken=%s";
        JSONObject jsonObject = JSONObject.parseObject(text);
        LOG.info("挪车通知模板："+jsonObject.toString());
        StringEntity stringEntity = new StringEntity(jsonObject.toString(), "UTF-8");
        pushUrl = String.format(pushUrl, informInfo.getPushToken());
        HttpClientUtil.sendPost(pushUrl, stringEntity);
        return Result.success("消息发送成功", true);
    }

    private String getChannelName(int channelCode) {
        String metaUrl = rootUrl+ "/public/api/channelMeta";
        String channelListStr = HttpClientUtil.sendGet(metaUrl);
        JSONObject jObject = JSONObject.parseObject(channelListStr);
        JSONArray arr = jObject.getJSONArray("data");
        assert arr != null;
        for (Object ele : arr) {
            JSONObject jsonEle = (JSONObject) ele;
            if("".equals(jsonEle.getString("channelType"))){
                continue;
            }
            if (channelCode == jsonEle.getInteger("channelType")) {
                return jsonEle.getString("name");
            }
        }
        return null;
    }

    @Override
    public Result<CarBaseInfoResp> getInform(String carToken) {
        if (null == carToken) {
            return Result.error("carToken不能为空", new CarBaseInfoResp());
        }
        CarBaseInfoResp carBaseInfoResp = new CarBaseInfoResp();
        InformInfo informInfo = carQRCodeMapper.selectInformByCarToken(carToken);
        if (null == informInfo) {
            LOG.error("carToken[ {} ]不存在", carToken);
            return Result.error("carToken不存在", new CarBaseInfoResp());
        }
        int limitIntervalTime = PlatformConfUtil.getInt(Constants.CASE_MOVECAR_ALLOW_INFORM_TIME_INTERVAL_EVERYDAY);
        long actualIntervalTime = (System.currentTimeMillis() - informInfo.getInformTime()) / 1000;
        carBaseInfoResp.setLicenceNum(informInfo.getLicenceNum());
        if (limitIntervalTime < actualIntervalTime) {
            carBaseInfoResp.setInformTime(0L);
            return Result.success(carBaseInfoResp);
        } else {
            carBaseInfoResp.setInformTime(limitIntervalTime - actualIntervalTime);
            return Result.fail(carBaseInfoResp);
        }
    }

    @Override
    public Result<String> genCarToken(CarInfoReq carInfoReq) {
        String pushToken = carInfoReq.getPushToken();
        String jsonString = String.format("{\"pushToken\":\"%s\"}", pushToken);

        // 检查pushToken是否存在
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");
        String ptcheckUrl = rootUrl+ "/public/api/ptcheck";
        String stateContent = HttpClientUtil.sendPost(ptcheckUrl, stringEntity);
        JSONObject stateJson = JSONObject.parseObject(stateContent);
        if (stateJson.getLong("code") != 0 || (stateJson.getLong("code") == 0 && !stateJson.getBoolean("data"))) {
            LOG.error("pushToken：[{}]不存在，无法创建carToken!", pushToken);
            return Result.fail("pushToken不存在", null);
        }

        // 根据pushToken获取userId
        String userIdUrl = rootUrl+ "/public/api/getid";
        String idContent = HttpClientUtil.sendPost(userIdUrl, stringEntity);
        JSONObject idJson = JSONObject.parseObject(idContent);
        if (idJson.getLong("code") != 0 || (idJson.getLong("code") == 0 && "".equals(idJson.getString("data")))) {
            return Result.fail("pushToken不存在,无法获取对应的useId", null);
        }

        // 检查用户是否开启通道
        String channelCheckUrl;
        String channelCheckUrlModel = rootUrl+ "/public/api/channelCanUse?pushToken=%s";
        if ("".equals(carInfoReq.getChannelType()) || null == carInfoReq.getChannelType()) {
            channelCheckUrl = String.format(channelCheckUrlModel, pushToken);
        } else {
            String combPushToken = String.join("-", pushToken, carInfoReq.getChannelType());
            channelCheckUrl = String.format(channelCheckUrlModel, combPushToken);
        }
        String channelStateContent = HttpClientUtil.sendGet(channelCheckUrl);
        JSONObject channelStateJson = JSONObject.parseObject(channelStateContent);
        if (channelStateJson.getLong("code") != 0 ||
                (channelStateJson.getLong("code") == 0 && !channelStateJson.getBoolean("data"))) {
            return Result.fail("通道未配置或者未开启,无法进行消息推送接收", null);
        }

        // 生成carToken
        String carToken = UUID.randomUUID().toString();
        String licence = carInfoReq.getLicence();
        String userId = idJson.getString("data");
        pushToken = remarkChannelType(pushToken, carInfoReq.getChannelType());
        int updateRows = carQRCodeMapper.insertOrUpdateCarToken(carToken, pushToken, licence, userId);
        if (updateRows == 1) {
            return Result.success("创建应用专属序列号成功", carToken);
        }
        return Result.fail("创建应用专属序列号失败", null);
    }

    private String remarkChannelType(String pushToken, String channelCode) {
        if (null != channelCode && !"".equals(channelCode)) {
            String channelName = getChannelName(Integer.parseInt(channelCode));
            switch (Objects.requireNonNull(channelName)) {
                case "wechat_app": // 企微应用
                case "wechat_group": // 企微群
                case "dingtalk_group": // 钉钉群
                case "feishu_group": // 飞书群
                case "e_mail": // 邮件
                    pushToken = String.join("-", pushToken, channelCode);
                    break;
                default:
                    LOG.error("channelType not right!");
                    pushToken = null;
            }

        }
        return pushToken;
    }
}
