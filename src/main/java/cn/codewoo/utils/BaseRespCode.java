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
    SYS_ERROR(50000,"系统错误"),
    OUT_TRADE_NO_NOT_FOUND(50001,"订单号不存在，请重新购买"),
    ORDER_OUT_TRADE_NO_EXISTS(50002,"存在同类型订单，请支付后再试"),
    ORDER_TRADE_SUCCESSFUL(50003,"以拥有该视频，请勿重复购买")
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
