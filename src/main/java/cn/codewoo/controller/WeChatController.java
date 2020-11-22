package cn.codewoo.controller;

import cn.codewoo.config.WeChatConfig;
import cn.codewoo.entity.VideoOrder;
import cn.codewoo.exception.CustomException;
import cn.codewoo.service.IVideoOrderService;
import cn.codewoo.utils.BaseRespCode;
import cn.codewoo.utils.DataResult;
import cn.codewoo.utils.VXPayUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @ClassName WeChatController
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/15 11:10 上午
 * @Version 1.0
 **/
@Controller
@RequestMapping("/api")
public class WeChatController {
    private final Logger log = LoggerFactory.getLogger(WeChatController.class);

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private IVideoOrderService videoOrderService;
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

    @ApiOperation("微信支付回调函数")
    @RequestMapping("/pub/wechat/order/callback")
    public void orderCallback(HttpServletRequest request, HttpServletResponse resp){
        SortedMap<String, String> respMap = new TreeMap<>();

        try{
            ServletInputStream inputStream = request.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            reader.close();
            inputStream.close();
            Map<String, String> map = VXPayUtils.xmlToMap(sb.toString());
            log.info("微信返回的数据：" + map.toString());

            //将map转换成sortedMap
            SortedMap<String, String> sortedMap = VXPayUtils.getSortedMap(map);
            //判断签名是否合法
            if (!VXPayUtils.isCorrectSign(sortedMap,weChatConfig.getKey())){
                log.error("签名不合法");
                throw new CustomException(BaseRespCode.ERROR);
            }
            //判断支付是否成功
            if ("SUCCESS".equals(sortedMap.get("result_map"))){
                VideoOrder videoOrder = videoOrderService.selectOrderByOutTradeNo(sortedMap.get("out_trade_no"));
                if (videoOrder != null){
                    VideoOrder newVideoOrder = new VideoOrder();
                    newVideoOrder.setState(1);
                    newVideoOrder.setNotifyTime(new Date());
                    videoOrderService.update(newVideoOrder);
                    respMap.put("return_code", "SUCCESS");
                    respMap.put("return_msg", "OK");
                }

            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
