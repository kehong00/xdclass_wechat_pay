package cn.codewoo.service.impl;

import cn.codewoo.config.AlipayConfig;
import cn.codewoo.entity.User;
import cn.codewoo.exception.CustomException;
import cn.codewoo.mapper.UserMapper;
import cn.codewoo.service.IUserService;
import cn.codewoo.utils.AlipayUtils;
import cn.codewoo.utils.BaseRespCode;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/15 8:41 下午
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements IUserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final Gson gson = new Gson();
    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private AlipayUtils alipayUtils;

    @Override
    public User saveAlipayUser(String auth_token) {
        //使用auth_token获取用户信息
        AlipayClient alipayClient = alipayUtils.getAlipayClient();

        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        try{
            AlipayUserInfoShareResponse response = alipayClient.execute(request, auth_token);
            logger.info(response.getBody());
            User user = userMapper.selectUserByOpenid(response.getUserId());
            if (user != null){
                return user;
            }
            User newUser = new User();
            newUser.setOpenid(response.getUserId());
            newUser.setName(response.getNickName());
            newUser.setHeadImg(response.getAvatar());
            newUser.setSex("m".equals(response.getGender()) ? 1:2);
            StringBuffer sb = new StringBuffer("四川").append("||").append("成都");
            newUser.setCity(sb.toString());
            newUser.setCreateTime(new Date());
            int rows = userMapper.insertSelective(newUser);
            if (rows == 1){
                return newUser;
            }else {
                throw new CustomException(BaseRespCode.SYS_ERROR);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new CustomException(BaseRespCode.ALIPAY_OPEN_AUTH_TOKEN_ERROR);
        }
    }
}
