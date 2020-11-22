package cn.codewoo.utils;

import cn.codewoo.config.AlipayConfig;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

/**
 * @ClassName AlipayUtils
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/16 6:58 下午
 * @Version 1.0
 **/
@Component
@Scope("singleton")
public class AlipayUtils {
    @Autowired
    private AlipayConfig alipayConfig;

    public AlipayClient getAlipayClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.getAlipayGateway(),
                alipayConfig.getAppId(), alipayConfig.getAppPrivateKey(), "json", "GBK", alipayConfig.getAlipayPublicKey(), "RSA2");
        return alipayClient;
    }

    public static String getUrlDecode(SortedMap<String, String> map){
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            if ("sign".equals(key) || "sign_type".equals(key)){
                continue;
            }
            sb.append(key + "=" + value + "&");
        }

        String substring = sb.toString().substring(0, sb.length() - 1);
        return substring;
    }

}
