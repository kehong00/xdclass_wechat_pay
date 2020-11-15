package cn.codewoo.controller;

import cn.codewoo.utils.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/pub")
    @ApiOperation("测试接口")
    public DataResult login(){
        return DataResult.success();
    }
}
