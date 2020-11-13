package cn.codewoo.utils.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author kehong
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperty {
    /**
     * subject 签发人
     * expire 过期时间
     * appSecret 秘钥
     */
    private String subject;
    private Duration expire;
    private String appSecret;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Duration getExpire() {
        return expire;
    }

    public void setExpire(Duration expire) {
        this.expire = expire;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
