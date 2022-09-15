package cn.dayutec.pigeon.utils.results;

public class Result<T> {
    private CodeNumEnum code;
    private String message;
    private T data;

    /**
     * for json deserialize
     */
    private Result() {
    }

    private Result(CodeMsg msg, T data) {
        this(msg);
        this.data = data;
    }

    public Result(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.message = codeMsg.getMessage();
        }
    }

    /**
     * 通用调用
     */
    public static <T> Result<T> of(CodeMsg codeMsg) {
        return new Result<>(codeMsg);
    }

    public static <T> Result<T> of(CodeMsg codeMsg, T data) {
        return new Result<>(codeMsg, data);
    }

    /**
     * 成功时候的调用，快捷调用
     */
    public static <T> Result<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(new CodeMsg(CodeNumEnum.SUCCESS, message), data);
    }

    public static <T> Result<T> success() {
        return new Result<>(CodeMsg.SUCCESS);
    }


    /**
     * 完成时候的调用，快捷调用
     */
    public static <T> Result<T> finish(T data) {
        return finish("操作完成", data);
    }

    public static <T> Result<T> finish(String message, T data) {
        return new Result<>(new CodeMsg(CodeNumEnum.FINISH, message), data);
    }

    public static <T> Result<T> finish() {
        return new Result<>(CodeMsg.FINISH);
    }

    /**
     * 失败时候的调用，快捷调用
     */
    public static <T> Result<T> fail(T data) {
        return fail("操作失败", data);
    }

    public static <T> Result<T> fail(String message, T data) {
        return new Result<>(new CodeMsg(CodeNumEnum.FAIL, message), data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(CodeMsg.FAIL);
    }

    /**
     * 错误时候的调用，快捷调用
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    public static <T> Result<T> error(String message, T data) {
        return new Result<>(new CodeMsg(CodeNumEnum.ERROR, message), data);
    }

    public static <T> Result<T> error() {
        return new Result<>(CodeMsg.ERROR);
    }

    public CodeNumEnum getCode() {
        return code;
    }

    public void setCode(CodeNumEnum code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }


    public T getData() {
        return data;
    }


}
