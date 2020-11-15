package cn.codewoo.utils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpsConfigurator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HttpClientUtils
 * @Description HttpClient工具类，封装get、post请求
 * @Author kehong
 * @Date 2020/11/15 1:13 下午
 * @Version 1.0
 **/
public class HttpClientUtils {
    private static final Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 发送get请求
     * @param url 请求地址
     * @param timeout 请求超时时间
     * @return 响应
     */
    public static Map<String, Object> doGet(String url, int timeout){
        Map<String, Object> map = new HashMap<>();
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setRedirectsEnabled(true)
                .build();
        httpGet.setConfig(requestConfig);
        try{
            HttpResponse response = httpClient.execute(httpGet);
            //判断请求状态码是否为200
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                String entityString = EntityUtils.toString(entity);
                map = gson.fromJson(entityString, map.getClass());
            }
        }catch (Exception e){
            logger.error("get请求失败",e);
        }
        return map;
    }

    /**
     * 发送post请求
     * @param url 请求地址
     * @param data 请求数据
     * @param timeout 超时时长
     * @return 响应数据
     */
    public static String doPost(String url, String data, int timeout){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost();
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setRedirectsEnabled(true)
                .build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type", MediaType.TEXT_HTML_VALUE);
        if (Strings.isEmpty(data) && data instanceof String){
            StringEntity entity = new StringEntity(data, "utf8");
            httpPost.setEntity(entity);
        }
        try{
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200){
                String result = EntityUtils.toString(response.getEntity());
                return result;
            }
        }catch (Exception e){
            logger.error("post请求失败：",e);
        }
        return null;
    }
}
