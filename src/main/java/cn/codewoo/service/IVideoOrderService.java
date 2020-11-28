package cn.codewoo.service;

import cn.codewoo.dto.OrderSaveDTO;
import cn.codewoo.entity.VideoOrder;
import cn.codewoo.vo.req.AlipayPayReqVO;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 视频订单服务接口
 * @author kehong
 */
public interface IVideoOrderService {
    /**
     * 使用redis生成订单号
     * @return
     */
    String generateOutTradeNo(String type);

    /**
     * 保存微信订单，生成支付二维码
     * @param dto
     * @return
     */
    String save(OrderSaveDTO dto);

    /**
     * 保存支付宝支付的订单
     * @param videoId
     * @param userId
     * @return
     */
    AlipayPayReqVO saveAlipay(int videoId,int userId) throws UnsupportedEncodingException;
//    int saveAlipay(OrderSaveDTO dto);



    /**
     * 更新订单状态
     * @param order
     * @return
     */
    int update(VideoOrder order);

    /**
     * 根据流水号查询订单
     * @param outTradeNo
     * @return
     */
    VideoOrder selectOrderByOutTradeNo(String outTradeNo);

    /**
     * 根据用户id查询订单
     * @param userId
     * @return
     */
    List<VideoOrder> selectOrderByUserId(int userId);

    /**
     * 根据用户id和视频id查询订单信息
     * @param userId
     * @param videoId
     * @return
     */
    VideoOrder selectOrderByUserIdAndVideoId(int userId,int videoId);



    /**
     * 生成支付宝支付业务参数
     * @param videoOrder
     * @return
     */
    AlipayPayReqVO generateAlipayPayReqVO(VideoOrder videoOrder);


    /**
     * 查询用户是否购买该视频
     * @param userId
     * @param videoId
     * @return
     */
    VideoOrder selectOrderStateByUserIdAndVideoId(int userId, int videoId);


}
