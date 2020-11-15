package cn.codewoo.shiro;

import cn.codewoo.constant.Constants;
import cn.codewoo.exception.CustomException;
import cn.codewoo.utils.BaseRespCode;
import cn.codewoo.utils.DataResult;
import cn.codewoo.utils.jwt.JwtUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName CustomTokenAccessFilter
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/13 8:43 下午
 * @Version 1.0
 **/
public class CustomTokenAccessFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String jwtToken = request.getHeader(Constants.AUTHENTICATION);
        if (!StringUtils.isEmpty(jwtToken)){
            try{
                Subject subject = this.getSubject(request, response);
                subject.login(new CustomToken(jwtToken));
                return true;
            }catch (AuthenticationException e){
                DataResult dataResult = DataResult.getDataResult(BaseRespCode.AUTHENTICATION_ERROR);
                customResponse(request,response,dataResult);
                return false;
            }
        }
        return false;
    }

    public void customResponse(ServletRequest request, ServletResponse response, DataResult dataResult){
        try{
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ServletOutputStream outputStream = response.getOutputStream();
            String jsonString = JSON.toJSONString(dataResult);
            outputStream.write(jsonString.getBytes("utf-8"));
        } catch (Exception e){
            throw new CustomException(BaseRespCode.SYS_ERROR);
        }
    }
}
