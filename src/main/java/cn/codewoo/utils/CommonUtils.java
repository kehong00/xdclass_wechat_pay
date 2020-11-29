package cn.codewoo.utils;

import cn.codewoo.constant.Constants;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * @ClassName CommonUtils
 * @Description 常用工具类封装
 * @Author kehong
 * @Date 2020/11/17 10:45 上午
 * @Version 1.0
 **/
public class CommonUtils {
    /**
     * 生成32位uuid
     * @return
     */
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
        return uuid;
    }

    public static String MD5(String data){
        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte [] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 获取请求中的token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request){
        //先从header中获取
        String token = request.getHeader(Constants.AUTHENTICATION);
        //如果header中没有
        if (token.isEmpty()){
            //从参数中获取
            token = request.getParameter(Constants.AUTHENTICATION);
        }
        return token;
    }


}
