package cn.codewoo.controller;

import cn.codewoo.config.WeChatConfig;
import cn.codewoo.utils.DataResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @ClassName WeChatController
 * @Description 微信接口
 * @Author kehong
 * @Date 2020/11/15 11:10 上午
 * @Version 1.0
 **/
@Controller
@RequestMapping("/api")
public class WeChatController {
    @Autowired
    private WeChatConfig weChatConfig;
    @GetMapping("/pub/wechat/login_url")
    @ApiOperation("拼装扫一扫登录二维码链接")
    @ResponseBody
    public DataResult loginUrl(@RequestParam(value = "accessPage", required = true) String accessPage) throws UnsupportedEncodingException {
        //获取重定向地址模板
        String redirectUrl = weChatConfig.getOpenRedirectUrl();
        //进行URLEncode编码
        String callbackUrl = URLEncoder.encode(redirectUrl,"GBK");
        String qrcodeUrl = String.format(weChatConfig.getOPEN_QRCODE_URL(),weChatConfig.getAppId()
                , callbackUrl,accessPage);
        return DataResult.success(qrcodeUrl);
    }
}
