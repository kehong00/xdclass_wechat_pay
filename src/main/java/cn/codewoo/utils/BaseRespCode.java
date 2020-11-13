package cn.codewoo.utils;

/**
 * @author kehong
 * 响应码枚举
 */

public enum BaseRespCode implements IBaseRespCode{
    //响应码
    SUCCESS(20000,"操作成功"),
    ERROR(40000,"操作失败"),
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
