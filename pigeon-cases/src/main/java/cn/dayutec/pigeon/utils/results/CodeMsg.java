package cn.dayutec.pigeon.utils.results;

public final class CodeMsg {
    //通用的错误码 CodeNumEnum
    public static final CodeMsg SUCCESS = new CodeMsg(CodeNumEnum.SUCCESS, "操作成功");
    public static final CodeMsg FINISH = new CodeMsg(CodeNumEnum.FINISH, "操作完成");
    public static final CodeMsg FAIL = new CodeMsg(CodeNumEnum.FAIL, "操作失败");
    public static final CodeMsg ERROR = new CodeMsg(CodeNumEnum.SERVER_ERROR, "服务端异常");


    private final CodeNumEnum code;
    private final String message;

    public CodeMsg(CodeNumEnum code, String message) {
        this.code = code;
        this.message = message;
    }

    public CodeNumEnum getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    /**
     * Generate a new CodeMessage.
     * thread-safe
     */
    public CodeMsg formatMsg(Object... args) {
        String messageResult = String.format(this.message, args);
        return new CodeMsg(this.code, messageResult);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code.getCode() + ", message=" + message + "]";
    }


}
