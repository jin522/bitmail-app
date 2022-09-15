package cn.dayutec.pigeon.utils.results;

/**
 * result返回模板统一定义
 */
public class ResultModel {

    /**
     * common
     */
    public static final CodeMsg PARAM_EMPTY_ERROR = new CodeMsg(CodeNumEnum.PARAM_ERROR, "参数校验异常,存在参数为空");
    public static final CodeMsg SQL_ERROR = new CodeMsg(CodeNumEnum.PARAM_ERROR, "操作失败，请核查提交的数据是否符合规则");
    public static final CodeMsg SQL_DUPLICATE_ERROR = new CodeMsg(CodeNumEnum.PARAM_ERROR, "操作失败，请核查提交的数据是否已存在");
    public static final CodeMsg PARAM_ERROR = new CodeMsg(CodeNumEnum.PARAM_ERROR, "参数输入异常");

    /**
     * authen拦截器
     */
    public static final CodeMsg TOKEN_EMPTY_ERROR = new CodeMsg(CodeNumEnum.TOKEN_ERROR, "需要Token，请重新登录！");
    public static final CodeMsg TOKEN_EXPIRED_ERROR = new CodeMsg(CodeNumEnum.TOKEN_ERROR, "您的账号凭证信息已失效，请重新登录!");
    public static final CodeMsg TOKEN_ILLEGAL_ERROR = new CodeMsg(CodeNumEnum.TOKEN_ERROR, "您的账号凭证信息非法，请重新登录！");
    public static final CodeMsg SINGLE_LOGIN_ERROR = new CodeMsg(CodeNumEnum.MULTI_LOGIN_ERROR, "您的账号已在其它地方登陆!");

    /**
     * auth包
     */
    public static final CodeMsg LOGIN_CODE_ERROR = new CodeMsg(CodeNumEnum.ERROR, "验证码输入错误！");
    public static final CodeMsg VALID_CODE_NOT_EXIST_WARN = new CodeMsg(CodeNumEnum.WARN, "验证码错误，禁止登陆！");
    public static final CodeMsg LOGIN_CODE_REPEAT_WARN = new CodeMsg(CodeNumEnum.WARN, "验证码重复，请重新获取验证码登录");
    public static final CodeMsg USER_NOT_ACCESS_RARN = new CodeMsg(CodeNumEnum.WARN, "用户已锁定，请%d分钟后重试");
    public static final CodeMsg ERROR_TIMES_WARN = new CodeMsg(CodeNumEnum.WARN, "密码输入错误，可尝试次数剩余%d次");
    public static final CodeMsg LOGOUT_ERROR = new CodeMsg(CodeNumEnum.ERROR, "用户信息为空");
    public static final CodeMsg LOGOUT_SUCCESS = new CodeMsg(CodeNumEnum.SUCCESS, "退出登录成功");
    public static final CodeMsg LOGIN_SUCCESS = new CodeMsg(CodeNumEnum.SUCCESS, "登录成功");
    public static final CodeMsg LOGIN_FAIL = new CodeMsg(CodeNumEnum.FAIL, "登录失败");
    public static final CodeMsg PUSHTOKEN_CREATE_ERROR = new CodeMsg(CodeNumEnum.ERROR, "生成pushToken失败");

    /**
     * client包
     */
    public static final CodeMsg USER_ID_NOTEXIST_FAIL = new CodeMsg(CodeNumEnum.FAIL, "用户身份验证失败");
    public static final CodeMsg CONF_PARA_NOTSET_WARN = new CodeMsg(CodeNumEnum.WARN, "未进行通道参数配置");
    public static final CodeMsg EMPTY_HISTORY_MSG_WARN = new CodeMsg(CodeNumEnum.WARN, "没有历史消息");
    public static final CodeMsg QUERY_HISTORY_MSG_SUCCESS = new CodeMsg(CodeNumEnum.SUCCESS, "查询历史消息成功");
    public static final CodeMsg VALID_CHANNEL_UP_LIMIT_ERROR = new CodeMsg(CodeNumEnum.ERROR, "最多同时只能开启%d个通道");
    public static final CodeMsg CHANNEL_NOT_SET_FAIL = new CodeMsg(CodeNumEnum.FAIL, "请先配置通道，再进行通道状态管理");
    //sub-邮箱
    public static final CodeMsg SEND_VERIFY_CODE_SUCCESS = new CodeMsg(CodeNumEnum.SUCCESS, "成功发送验证码，请前往邮箱中查看");
    public static final CodeMsg SEND_VERIFY_CODE_FAIL = new CodeMsg(CodeNumEnum.FAIL, "发送验证码到邮箱失败，请稍后再试");
    public static final CodeMsg GET_VERIFY_CODE_TOO_FAST_ERROR = new CodeMsg(CodeNumEnum.ERROR, "获取验证码频繁，请稍后再试");
    public static final CodeMsg VERIFY_CODE_EXPIRED_FAIL = new CodeMsg(CodeNumEnum.FAIL, "验证码为空或者已超时，请重新获取验证码");
    public static final CodeMsg EMAIL_SET_SUCCESS = new CodeMsg(CodeNumEnum.SUCCESS, "邮箱验证成功");
    public static final CodeMsg EMAIL_NOT_SAME_ERROR = new CodeMsg(CodeNumEnum.ERROR, "邮箱错误，请确保设置邮箱和接收验证码邮箱一致");
    public static final CodeMsg VERIFY_CODE_ERROR = new CodeMsg(CodeNumEnum.ERROR, "验证码错误，邮箱验证失败，请重新获取验证码");
    //sub-第三方接口
    public static final CodeMsg TOKEN_WITH_LOGOUT_FAIL = new CodeMsg(CodeNumEnum.FAIL, "用户登录状态为：退出登录，token信息无效，请登陆后重试！");
    public static final CodeMsg PUSHTOKEN_NOT_EXIST_ERROR = new CodeMsg(CodeNumEnum.ERROR, "PushToken不存在");

    /**
     * server包
     */
    public static final CodeMsg CHANNEL_NOT_SET_OR_OPEN_FAIL = new CodeMsg(CodeNumEnum.FAIL, "没有配置或开启通道");
    public static final CodeMsg MINUTE_COUNT_OVER_FAIL = new CodeMsg(CodeNumEnum.FAIL, "信息发送频率太快，请等待一会再试！");
    public static final CodeMsg DAY_COUNT_OVER_FAIL = new CodeMsg(CodeNumEnum.FAIL, "已达当日消息发送条数上限，请明日再试！");
    public static final CodeMsg MSG_SEND_SUCCESS = new CodeMsg(CodeNumEnum.SUCCESS, "消息发送成功");

}
