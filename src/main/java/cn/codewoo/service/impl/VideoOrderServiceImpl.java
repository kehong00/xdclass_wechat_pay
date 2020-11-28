package cn.codewoo.service.impl;

import cn.codewoo.config.WeChatConfig;
import cn.codewoo.dto.OrderSaveDTO;
import cn.codewoo.entity.User;
import cn.codewoo.entity.Video;
import cn.codewoo.entity.VideoOrder;
import cn.codewoo.exception.CustomException;
import cn.codewoo.mapper.UserMapper;
import cn.codewoo.mapper.VideoMapper;
import cn.codewoo.mapper.VideoOrderMapper;
import cn.codewoo.service.IVideoOrderService;
import cn.codewoo.utils.BaseRespCode;
import cn.codewoo.utils.CommonUtils;
import cn.codewoo.utils.HttpClientUtils;
import cn.codewoo.utils.VXPayUtils;
import cn.codewoo.vo.req.AlipayPayReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName VideoOrderServiceImpl
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/17 10:26 上午
 * @Version 1.0
 **/
@Service
public class VideoOrderServiceImpl implements IVideoOrderService {
    private final Logger log = LoggerFactory.getLogger(VideoOrderServiceImpl.class);

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired(required = false)
    VideoOrderMapper orderMapper;

    @Autowired(required = false)
    private VideoMapper videoMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 生成验证码
     * @param type：订单类型
     * @return：返回生成的订单号
     */
    @Override
    public String generateOutTradeNo(String type) {
        //设置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYMMddHHmm");
        //获取当前时间，格式化为设置的格式：200717
        String curDate = dateFormat.format(System.currentTimeMillis());
        //使用特定类型+日期作为key，防止重复，每生成一次value加1
        Long number = redisTemplate.opsForValue().increment(type + curDate);
        //得到补位过后的字符串
        String padString = padRight(number.toString(), 7, "0");
        //将字符串拼接成需要的格式，并返回
        return type + curDate + padString;
    }

    @Override
    public String save(OrderSaveDTO dto) {
        //查询用户购买的视频
        Video video = videoMapper.selectByPrimaryKey(dto.getVideoId());
        if (video == null){
            return null;
        }

        //查询用户信息
        User user = userMapper.selectByPrimaryKey(dto.getUserId());
        if (user == null){
            return null;
        }

        //Mock数据，订单信息

        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setOutTradeNo(generateOutTradeNo("1001"));
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoImg(video.getCoverImg());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setCreateTime(new Date());
        videoOrder.setVideoId(video.getId());
        videoOrder.setState(0);

        videoOrder.setUserId(user.getId());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setNickname(user.getName());
        videoOrder.setIp(dto.getIp());

        //生成签名
        String order_url = unifiedOrder(videoOrder);
        log.info("支付二维码：" + order_url);


        int rows = orderMapper.insertSelective(videoOrder);
        if (rows != 1){
            throw new CustomException(BaseRespCode.SYS_ERROR);
        }
        return order_url;
    }

    @Override
    public AlipayPayReqVO saveAlipay(int videoId, int userId) throws UnsupportedEncodingException {

        //查询用户购买的视频
        Video video = videoMapper.selectByPrimaryKey(videoId);
        if (video == null){
            return null;
        }

        //查询用户信息
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null){
            return null;
        }

        //查询用户是否有该类型的订单，如果有需要支付或删除订单'
        VideoOrder videoOrder = orderMapper.selectOrderByUserIdAndVideoId(userId, videoId);
        if (videoOrder != null){
            throw new CustomException(BaseRespCode.ORDER_OUT_TRADE_NO_EXISTS);

        }

        VideoOrder dto = new VideoOrder();
        dto.setOpenid(user.getOpenid());
        dto.setOutTradeNo(generateOutTradeNo("1001"));
        dto.setState(0);
        dto.setCreateTime(new Date());
        dto.setTotalFee(video.getPrice());
        dto.setNickname(user.getName());
        dto.setHeadImg(user.getHeadImg());
        dto.setVideoId(video.getId());
        dto.setVideoTitle(video.getTitle());
        dto.setVideoImg(video.getCoverImg());
        dto.setUserId(user.getId());
        dto.setIp("42.194.140.230");


        AlipayPayReqVO alipayPayReqVO = generateAlipayPayReqVO(dto);


        orderMapper.insertSelective(dto);

        return alipayPayReqVO;

    }



    @Override
    public int update(VideoOrder order) {
        int rows = orderMapper.updateByOutTradeNo(order);
        return rows;
    }

    @Override
    public VideoOrder selectOrderByOutTradeNo(String outTradeNo) {
        VideoOrder videoOrder = orderMapper.selectOrderByOutTradeNo(outTradeNo);
        return videoOrder;
    }

    @Override
    public List<VideoOrder> selectOrderByUserId(int userId) {
        return orderMapper.selectOrderByUserId(userId);
    }

    @Override
    public VideoOrder selectOrderByUserIdAndVideoId(int userId, int videoId) {
        return orderMapper.selectOrderByUserIdAndVideoId(userId,videoId);
    }

    @Override
    public AlipayPayReqVO generateAlipayPayReqVO(VideoOrder videoOrder) {
        AlipayPayReqVO alipayPayReqVO = new AlipayPayReqVO();
        alipayPayReqVO.setSubject("test");
        alipayPayReqVO.setBody("test");
        alipayPayReqVO.setProduct_code("FAST_INSTANT_TRADE_PAY");
        alipayPayReqVO.setOut_trade_no(videoOrder.getOutTradeNo());
        alipayPayReqVO.setTotal_amount((double)videoOrder.getTotalFee() / 100);
        return alipayPayReqVO;
    }


    @Override
    public VideoOrder selectOrderStateByUserIdAndVideoId(int userId, int videoId) {
        return orderMapper.selectOrderByUserIdAndVideoId(userId,videoId);
    }


    /**
     * 向字符串左边补位，如果原字符串比需求的长度小，则在原字符串左边添加alexin定义的字符
     * @param ordString：原始字符串
     * @param len：需求字符串的长度
     * @param alexin：补位占用的字符
     * @return：返回得到的字符串
     */
    public String padRight(String ordString,int len,String alexin){
        StringBuffer s = new StringBuffer("");
        //需求长度-实际长度得到的差，如果>0则需要补位，差值就是需要的补位的长度
        for (int i = 0; i < len - ordString.length(); i++) {
            s.append(alexin);
        }
        return s + ordString;
    }

    /**
     * 统一下单地址，根据订单信息获取支付二维码
     * @param videoOrder
     * @return
     */
    private String unifiedOrder(VideoOrder videoOrder){
        //将订单信息按ASCII字典进行排序
        SortedMap<String, String> params = new TreeMap<>();
        params.put("appid",weChatConfig.getAppId());
        params.put("mch_id",weChatConfig.getMchId());
        params.put("nonce_str", CommonUtils.generateUUID());
        params.put("body",videoOrder.getVideoTitle());
        params.put("out_trade_no",videoOrder.getOutTradeNo());
        params.put("total_fee", String.valueOf(videoOrder.getTotalFee()));
        params.put("spbill_create_ip",videoOrder.getIp());
        params.put("notify_url",weChatConfig.getCallbackUrl());
        params.put("trade_type","NATIVE");

        //进行签名
        String sign = VXPayUtils.createSign(params, weChatConfig.getKey());

        //添加签名到map中
        params.put("sign",sign);

        try {
            //将map转换为微信支持的xml
            String mapToXml = VXPayUtils.mapToXml(params);
            log.info(mapToXml);
            //向统一下单接口发送请求，获取支付二维码
            String orderUrl = HttpClientUtils.doPost(weChatConfig.getUnifiedOrderUrl(), mapToXml, 5000);
            if (StringUtils.isEmpty(orderUrl)){
                return null;
            }
            Map<String, String> orderMap = VXPayUtils.xmlToMap(orderUrl);
            log.info(orderMap.toString());
            if (orderMap != null){
                return orderMap.get("code_url");
            }

        } catch (Exception e) {

        }

        return "";
    }
}
