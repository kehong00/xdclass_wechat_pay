package cn.codewoo.utils;


/**
 * @author kehong
 * 统一返回数据格式封装类
 */
public class DataResult {
    /**
     * 响应码
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public DataResult() {
    }

    /**
     * 全参构造
     * @param code
     * @param msg
     * @param data
     */
    public DataResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 结束自定义响应码
     * @param respCode
     */
    public DataResult(IBaseRespCode respCode, Object data){
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.data = data;
    }




    /**
     * 获取默认成功，不含数据返回对象
     * @return
     */
    public static DataResult success(){
        return new DataResult(BaseRespCode.SUCCESS,null);
    }

    /**
     * 获取默认成功，有返回数据的对象
     * @param data
     * @return
     */
    public static DataResult success(Object data){
        return new DataResult(BaseRespCode.SUCCESS,data);
    }

    /**
     * 获取默认失败，不含数据返回对象
     * @return
     */
    public static DataResult error(){
        return new DataResult(BaseRespCode.ERROR,null);
    }

    /**
     * 获取默认失败，有返回数据的对象
     * @param data
     * @return
     */
    public static DataResult error(Object data){
        return new DataResult(BaseRespCode.ERROR,data);
    }

    /**
     * 通过自定义code获取不含返回数据的封装对象
     * @param respCode
     * @return
     */
    public static DataResult getDataResult(IBaseRespCode respCode){
        return new DataResult(respCode,null);
    }

    /**
     * 通过自定义code获取有返回数据的封装对象
     * @param respCode
     * @param data
     * @return
     */
    public static DataResult getDataResult(IBaseRespCode respCode, Object data){
        return new DataResult(respCode,data);
    }

    /**
     * 自定义不含返回数据的封装对象
     * @param code
     * @param msg
     * @return
     */
    public static DataResult getDataResult(int code, String msg){
        return new DataResult(code,msg,null);
    }

    /**
     * 自定义有返回数据的封装对象
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static DataResult getDataResult(int code, String msg, Object data){
        return  new DataResult(code, msg, data);
    }
}
