package cn.codewoo.controller;

import cn.codewoo.config.AlipayConfig;
import cn.codewoo.constant.Constants;
import cn.codewoo.dto.OrderSaveDTO;
import cn.codewoo.entity.User;
import cn.codewoo.entity.Video;
import cn.codewoo.entity.VideoOrder;
import cn.codewoo.exception.CustomException;
import cn.codewoo.service.IUserService;
import cn.codewoo.service.IVideoOrderService;
import cn.codewoo.service.IVideoService;
import cn.codewoo.utils.AlipayUtils;
import cn.codewoo.utils.BaseRespCode;
import cn.codewoo.utils.DataResult;
import cn.codewoo.utils.jwt.JwtUtils;
import cn.codewoo.vo.req.AlipayPayReqVO;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @ClassName AlipayController
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/15 5:02 下午
 * @Version 1.0
 **/
@Controller
@RequestMapping("/api/")
public class AlipayController {
    private Logger logger = LoggerFactory.getLogger(AlipayController.class);
    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private AlipayUtils alipayUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVideoOrderService videoOrderService;

    @Autowired
    private IVideoService videoService;


    @GetMapping("/pub/alipay/login_qrurl")
    @ResponseBody
    public DataResult loginQrUrl() throws UnsupportedEncodingException {
        String qrurl = alipayConfig.getQrurl();
        String redirect = URLEncoder.encode(alipayConfig.getRedirectUrl(), "UTF-8");
        String loginRrUrl = String.format(qrurl, alipayConfig.getAppId(), redirect);
        return DataResult.success(loginRrUrl);

    }

    @GetMapping("/pub/alipay/user_callback")
    @ResponseBody
//    http://alipay.codewoo.cn/?app_id=2021002111631162&source=alipay_wallet&userOutputs=auth_user&scope=auth_user&alipay_token=&auth_code=f236e785c25845f9b257363f0c8fSC08
    public DataResult userCallback(@RequestParam(name = "app_id", required = true) String app_id
            , @RequestParam(name = "auth_code", required = false) String auth_code
            , @RequestParam(name = "scope", required = true) String scope, HttpServletRequest req, HttpServletResponse response) {
        logger.info(app_id);
        logger.info(auth_code);
        logger.info(scope);


        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(auth_code);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayUtils.getAlipayClient().execute(request);
            logger.info("获取到的auth_token:" + oauthTokenResponse.getAccessToken());
            User user = userService.saveAlipayUser(oauthTokenResponse.getAccessToken());
            if (user == null) {
                throw new CustomException(BaseRespCode.ALIPAY_OPEN_AUTH_TOKEN_ERROR);
            }
            String jwtToken = JwtUtils.geneJsonWebToken(user);
            logger.info("生成的token：" + jwtToken);
            response.sendRedirect("http://localhost:9003/#/token" + "?token=" + jwtToken);
            return DataResult.success(jwtToken);
        } catch (AlipayApiException | IOException e) {
            //处理异常
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/pub/alipay/pay")
    public void alipayPay(HttpServletRequest request, HttpServletResponse response, @RequestParam("video_id") Integer videoId) throws IOException {
//        String token = request.getHeader(Constants.AUTHENTICATION);
        String token = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJrZWhvbmciLCJpZCI6MywibmFtZSI6IueMquWktOeariIsImltZyI6Imh0dHBzOi8vdGZzLmFsaXBheS5uZXQvaW1hZ2VzL3BhcnRuZXIvVEIxc0FiZFhoVTV4ZTVKdmZHZVhYWFR1cFhhIiwiaWF0IjoxNjA2NTMyNjU3LCJleHAiOjE2MDY2MTkwNTd9.-A26FPCD_OFoDWZsUexD9-btJ1rTYmvBLXhLbYSKiqsUs5PAymd5WLNKDJbXsxnc";
        Claims claims = JwtUtils.checkJWT(token);

        int id = (int)claims.get("id");

        AlipayPayReqVO alipayPayReqVO = videoOrderService.saveAlipay(videoId, id);


        AlipayClient alipayClient = alipayUtils.getAlipayClient();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //支付成功同步回调地址
        alipayRequest.setReturnUrl(alipayConfig.getPay_return_url());
        //支付宝异步通知接口
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());


        String jsonString = JSON.toJSONString(alipayPayReqVO);

        alipayRequest.setBizContent(jsonString);
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.getWriter().write(form); //直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    /*@GetMapping("/pub/alipay/pay")
    public DataResult alipayPay(HttpServletRequest request, HttpServletResponse response, @RequestParam("video_id") Integer videoId) throws IOException {
        AlipayPayReqVO vo = new AlipayPayReqVO();
        vo.setBody("test");
        vo.setSubject("test");
        vo.setProduct_code("FAST_INSTANT_TRADE_PAY");
        vo.setTotal_amount(22.22);
        vo.setOut_trade_no(videoOrderService.generateOutTradeNo("1001"));


        int userId = 3;
        OrderSaveDTO dto = new OrderSaveDTO();
        dto.setUserId(userId);
        dto.setOutTradeNo(vo.getOut_trade_no());
        dto.setVideoId(videoId);
        dto.setTotalFee((int) (vo.getTotal_amount() * 100));
        dto.setIp("42.194.140.230");

        videoOrderService.saveAlipay(dto);


        AlipayClient alipayClient = alipayUtils.getAlipayClient();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://localhost:8081/api/pub/alipay/redirect");
        alipayRequest.setNotifyUrl("http://kt86j7.natappfree.cc/api/pub/alipay/pay_callback"); //在公共参数中设置回跳和通知地址


        String jsonString = JSON.toJSONString(vo);

        *//*alipayRequest.setBizContent( "{"  +
                "    \"out_trade_no\":\"20153420020101001\","  +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\","  +
                "    \"total_amount\":7599,"  +
                "    \"subject\":\"Iphone12 256G\","  +
                "    \"body\":\"Iphone12 256G\","  +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","  +
                "    \"extend_params\":{"  +
                "    \"sys_service_provider_id\":\"2088213833607846\""  +
                "    }" +
                "  }" );*//*
        alipayRequest.setBizContent(jsonString);
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.getWriter().write(form); //直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
        return null;
    }*/





    @RequestMapping("/pub/alipay/notify_callback")
    @ResponseBody
    /**
     * 接收支付宝异步通知的接口，处理订单支付状态
     */
    public void queryAlipayTradeState(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        SortedMap<String,String> map = new TreeMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        Iterator<Map.Entry<String, String[]>> iterator = entries.iterator();

        //获取通知参数，放入map中等待验签
        while(iterator.hasNext()){
            Map.Entry<String, String[]> entry = iterator.next();
            String key = entry.getKey();
            String[] value = entry.getValue();
            logger.info(key + " : " + Arrays.toString(value));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < value.length; i++) {
                //如果是最后一个参数，拼接时不需要","
                if (i == (value.length - 1)) {
                    sb.append(value[i]);
                } else {
                    sb.append(value[i]).append(",");
                }
            }
            map.put(key,sb.toString());
        }

        //使用Api进行验签
        boolean isCurrentSign = AlipaySignature.rsa256CheckContent(AlipayUtils.getUrlDecode(map), map.get("sign"), alipayConfig.getAlipayPublicKey(), "UTF-8");
        logger.info("验签结果：" + isCurrentSign);
        if (isCurrentSign){
            //获取订单状态
            String trade_status = map.get("trade_status");
            //验签成功
            String out_trade_no = map.get("out_trade_no");
            VideoOrder videoOrder = videoOrderService.selectOrderByOutTradeNo(out_trade_no);
            //判断订单是否有效
            if (videoOrder != null){
                //订单有效
                Integer totalFee = videoOrder.getTotalFee();
                double total_amount1 = Float.valueOf(map.get("total_amount")) * 100;
                int total_amount = (int) (Float.valueOf(map.get("total_amount")) * 100);

                String appId = map.get("app_id");

                //判断用户支付金额和appId是否一致
                if (totalFee == total_amount && alipayConfig.getAppId().equals(appId)){
                    //根据订单状态分别进行处理
                    if (videoOrder.getState() == 1){
                        response.getWriter().print("success");
                    }
                    if ("TRADE_SUCCESS".equals(trade_status)){
                        //订单支付成功
                        videoOrder.setNotifyTime(new Date());
                        videoOrder.setState(1);
                        int rows = videoOrderService.update(videoOrder);
                    }else if ("TRADE_FINISHED".equals(trade_status)){
                        videoOrder.setNotifyTime(new Date());
                        videoOrder.setState(1);
                        int rows = videoOrderService.update(videoOrder);
                    }
                    response.getWriter().print("success");
                }
            }
        }
    }

    @GetMapping("/pub/alipay/query_trade_state")
    @ResponseBody
    public DataResult alipayPayRedirect(HttpServletRequest request,@RequestParam String json){
        try {
            /*Map<String, String[]> parameterMap = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
            Iterator<Map.Entry<String, String[]>> iterator = entries.iterator();
            SortedMap sortedMap = new TreeMap();
            while (iterator.hasNext()){
                Map.Entry<String, String[]> next = iterator.next();
                String key = next.getKey();
                String[] value = next.getValue();
                sortedMap.put(key,value);
            }

            String jsonString = JSON.toJSONString(sortedMap);*/

            logger.info("接收到的json: " + json);
            String decode = URLDecoder.decode(json, "UTF-8");
            logger.info("decode url : " + decode);

            AlipayClient alipayClient = alipayUtils.getAlipayClient();
            AlipayTradePagePayRequest tradeReq = new AlipayTradePagePayRequest();
//            tradeReq.setBizContent(jsonString);
            tradeReq.setBizContent(decode);
            AlipayTradePagePayResponse response = alipayClient.pageExecute(tradeReq);
            if (response.isSuccess()){
                return DataResult.success();
            }else {
                return DataResult.error();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(BaseRespCode.SYS_ERROR);
        }

    }

    @ApiOperation("重新支付接口")
    @GetMapping("/auth/alipay/repay")
    public void rePay(@RequestParam String out_trade_no, HttpServletResponse response){
        VideoOrder videoOrder = videoOrderService.selectOrderByOutTradeNo(out_trade_no);
        if (videoOrder == null){
            throw new CustomException(BaseRespCode.OUT_TRADE_NO_NOT_FOUND);
        }

        AlipayClient alipayClient = alipayUtils.getAlipayClient();
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //支付成功同步回调地址
        alipayRequest.setReturnUrl(alipayConfig.getPay_return_url());
        //支付宝异步通知接口
        alipayRequest.setNotifyUrl(alipayConfig.getNotify_url());

        AlipayPayReqVO alipayPayReqVO = videoOrderService.generateAlipayPayReqVO(videoOrder);

        String jsonString = JSON.toJSONString(alipayPayReqVO);

        alipayRequest.setBizContent(jsonString);
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody();  //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        try{
            response.getWriter().write(form); //直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
