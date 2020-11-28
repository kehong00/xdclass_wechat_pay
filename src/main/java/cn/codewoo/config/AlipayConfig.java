package cn.codewoo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName AlipayConfig
 * @Description 支付宝开放接口配置
 * @Author kehong
 * @Date 2020/11/15 3:28 下午
 * @Version 1.0
 **/
@Configuration
@PropertySource(value = "classpath:application.yml")
public class AlipayConfig {
    @Value("${alipay.alipayGateway}")
    private String alipayGateway;
    @Value("${alipay.appId}")
    private String appId;
    @Value("${alipay.appPrivateKey}")
    private String appPrivateKey;
    @Value("${alipay.appPublicKey}")
    private String appPublicKey;
    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;
    @Value("${alipay.qrurl}")
    private String qrurl;
    @Value("${alipay.redirect_url}")
    private String redirectUrl;

    @Value("${notify_url}")
    private String notify_url;

    @Value("${pay_return_url}")
    private String pay_return_url;


    public String getAlipayGateway() {
        return alipayGateway;
    }

    public void setAlipayGateway(String alipayGateway) {
        this.alipayGateway = alipayGateway;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAppPublicKey() {
        return appPublicKey;
    }

    public void setAppPublicKey(String appPublicKey) {
        this.appPublicKey = appPublicKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }


    public String getQrurl() {
        return qrurl;
    }

    public void setQrurl(String qrurl) {
        this.qrurl = qrurl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getPay_return_url() {
        return pay_return_url;
    }

    public void setPay_return_url(String pay_return_url) {
        this.pay_return_url = pay_return_url;
    }
}
