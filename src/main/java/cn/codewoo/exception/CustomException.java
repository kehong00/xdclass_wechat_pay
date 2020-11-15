package cn.codewoo.exception;

import cn.codewoo.utils.IBaseRespCode;

/**
 * @ClassName CustomException
 * @Description 自定义异常类
 * @Author kehong
 * @Date 2020/11/14 1:40 下午
 * @Version 1.0
 **/
public class CustomException extends RuntimeException {
    private int code;
    private String msg;

    public CustomException() {
        super();
    }

    public CustomException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CustomException(IBaseRespCode respCode){
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
