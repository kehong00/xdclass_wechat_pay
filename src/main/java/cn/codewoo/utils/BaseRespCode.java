package cn.codewoo.utils;

/**
 * @author kehong
 * 响应码枚举
 */

public enum BaseRespCode implements IBaseRespCode{
    //响应码
    SUCCESS(20000,"操作成功"),
    ERROR(40000,"操作失败"),
    AUTHENTICATION_ERROR(400001, "认证失败，请重新登录"),
    TOKEN_NOT_NULL(40004,"请登录后再操作"),
    TOKEN_ERROR(40005,"登录失效，请重新登录"),
    ALIPAY_OPEN_AUTH_TOKEN_ERROR(40002,"支付宝登录失败"),
    SYS_ERROR(50000,"系统错误")
    ;

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;

    BaseRespCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
