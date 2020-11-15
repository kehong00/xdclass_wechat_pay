package cn.codewoo.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @ClassName CustomToken
 * @Description 自定义token，使用jwt进行认证
 * @Author kehong
 * @Date 2020/11/13 8:28 下午
 * @Version 1.0
 **/
public class CustomToken extends UsernamePasswordToken {
    private String token;

    public CustomToken() {
        super();
    }

    public CustomToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.getToken();
    }
}
