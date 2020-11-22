package cn.codewoo.controller;

import cn.codewoo.dto.OrderSaveDTO;
import cn.codewoo.service.IVideoOrderService;
import cn.codewoo.utils.DataResult;
import cn.codewoo.utils.IpUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName OrderController
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/17 2:57 下午
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private IVideoOrderService videoOrderService;
    @GetMapping("/add")
    public DataResult saveOrder(@RequestParam(value = "video_id",required = true) Integer videoId,
                                HttpServletRequest request, HttpServletResponse response) {
        String ipAddr = IpUtils.getIpAddr(request);
//        int userId = request.getAttribute("user_id");
        int userId = 3;
        OrderSaveDTO dto = new OrderSaveDTO();
        dto.setUserId(userId);
        dto.setVideoId(videoId);
        dto.setIp("42.194.140.230");
        String qrUrl = videoOrderService.save(dto);

        if (qrUrl == null){
            throw new NullPointerException();
        }

        try{
            //生成二维码配置
            Map<EncodeHintType,Object> hints =  new HashMap<>();

            //设置纠错等级
            hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.L);
            //编码类型
            hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUrl,BarcodeFormat.QR_CODE,400,400,hints);
            OutputStream out =  response.getOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix,"png",out);

        }catch (Exception e){
            e.printStackTrace();
        }

        return DataResult.success();
    }
}
