package cn.codewoo.service;

import cn.codewoo.dto.OrderSaveDTO;
import cn.codewoo.entity.VideoOrder;

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
     * @param dto
     * @return
     */
    int saveAlipay(OrderSaveDTO dto);

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

}
