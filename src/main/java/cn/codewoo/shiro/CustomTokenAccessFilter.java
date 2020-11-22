package cn.codewoo.shiro;

import cn.codewoo.constant.Constants;
import cn.codewoo.exception.CustomException;
import cn.codewoo.utils.BaseRespCode;
import cn.codewoo.utils.DataResult;
import cn.codewoo.utils.jwt.JwtUtils;
import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;


import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;

/**
 * @ClassName CustomTokenAccessFilter
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/13 8:43 下午
 * @Version 1.0
 **/
public class CustomTokenAccessFilter extends AccessControlFilter {
    private final Logger log = LoggerFactory.getLogger(CustomTokenAccessFilter.class);
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try{
            log.info("拦截到的地址是：" + request.getRequestURL());
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()){
                log.info(request.getHeader(headerNames.nextElement()));
            }
            String token = request.getHeader(Constants.AUTHENTICATION);
            if (Strings.isEmpty(token)){
                throw new CustomException(BaseRespCode.TOKEN_NOT_NULL);
            }
            //获得主体
            Subject subject = this.getSubject(servletRequest, servletResponse);
            //提交认证
            subject.login(new CustomToken(token));
        }catch (CustomException e){
            this.customResponse(servletRequest,servletResponse,DataResult.getDataResult(e.getCode(),e.getMsg()));
            log.error("发生异常："+e.getMsg(),e);
            return false;
        }catch (AuthenticationException e){
            if (e.getCause() instanceof CustomException){
                CustomException businessException = (CustomException) e.getCause();
                this.customResponse(servletRequest,servletResponse,DataResult.getDataResult(businessException.getCode(),businessException.getMsg()));
                log.error("发生异常："+businessException.getMsg(),e);
            }
            this.customResponse(servletRequest,servletResponse,DataResult.getDataResult(BaseRespCode.TOKEN_ERROR));
            log.error("发生认证异常",e);
            return false;
        }catch (Exception e){
            this.customResponse(servletRequest,servletResponse,DataResult.getDataResult(BaseRespCode.SYS_ERROR));
            log.error("发生系统异常",e);
            return false;
        }
        return true;
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
