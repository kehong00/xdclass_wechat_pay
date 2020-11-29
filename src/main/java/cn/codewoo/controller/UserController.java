package cn.codewoo.controller;

import cn.codewoo.config.WeChatConfig;
import cn.codewoo.constant.Constants;
import cn.codewoo.entity.User;
import cn.codewoo.exception.CustomException;
import cn.codewoo.mapper.UserMapper;
import cn.codewoo.utils.BaseRespCode;
import cn.codewoo.utils.CommonUtils;
import cn.codewoo.utils.DataResult;
import cn.codewoo.utils.jwt.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/14 1:58 下午
 * @Version 1.0
 **/
@Api(tags = "用户接口")
@RequestMapping("/api/")
@RestController
public class UserController {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    private WeChatConfig weChatConfig;
    @GetMapping("/pub")
    @ApiOperation("测试接口")
    public DataResult login(){
        return DataResult.success(weChatConfig.getAppId());
    }

    @GetMapping("/auth/test")
    public DataResult getUserById(@RequestParam(name = "id") Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        return DataResult.success(user);
    }

    @GetMapping("/auth/userinfo")
    public DataResult getUserInfo(HttpServletRequest request){
//        String token = request.getHeader(Constants.AUTHENTICATION);
        String token = CommonUtils.getToken(request);
        if (Strings.isEmpty(token)){
            throw new CustomException(BaseRespCode.TOKEN_NOT_NULL);
        }
        JwtUtils.checkJWT(token).get("id");
        return null;
    }
}
