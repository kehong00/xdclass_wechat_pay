package cn.codewoo.service;

import cn.codewoo.entity.User;

/**
 * 用户业务接口
 * @author kehong
 */
public interface IUserService {
    /**
     * 更加支付宝返回的auth_token获取用户信息
     * @param auth_token
     * @return
     */
    User saveAlipayUser(String auth_token);
}
