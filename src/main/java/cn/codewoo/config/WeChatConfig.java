package cn.codewoo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName WeChatConfig
 * @Description 微信配置类
 * @Author kehong
 * @Date 2020/11/15 11:01 上午
 * @Version 1.0
 **/
@Configuration
@PropertySource(value = "classpath:application.yml")
public class WeChatConfig {
    private final String OPEN_QRCODE_URL= "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";

    private final String unifiedOrderUrl = "https://api.xdclass.net/pay/unifiedorder";

    @Value("${wxpay.appid}")
    private String appId;
    @Value("${wxpay.appsecret}")
    private String appSecret;
    @Value("${wxopen.appid}")
    private String openAppId;
    @Value("${wxopen.appsecret}")
    private String openAppSecret;

    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;

    @Value("${wxpay.mer_id}")
    private String mchId;
    @Value("${wxpay.key}")
    private String key;
    @Value("${wxpay.callback}")
    private String callbackUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getOpenAppId() {
        return openAppId;
    }

    public void setOpenAppId(String openAppId) {
        this.openAppId = openAppId;
    }

    public String getOpenAppSecret() {
        return openAppSecret;
    }

    public void setOpenAppSecret(String openAppSecret) {
        this.openAppSecret = openAppSecret;
    }

    public String getOpenRedirectUrl() {
        return openRedirectUrl;
    }

    public void setOpenRedirectUrl(String openRedirectUrl) {
        this.openRedirectUrl = openRedirectUrl;
    }

    public String getOPEN_QRCODE_URL() {
        return OPEN_QRCODE_URL;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getUnifiedOrderUrl() {
        return unifiedOrderUrl;
    }
}
