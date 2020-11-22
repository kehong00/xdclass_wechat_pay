package cn.codewoo.exception;

import cn.codewoo.utils.BaseRespCode;
import cn.codewoo.utils.DataResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName CustomExceptionHandler
 * @Description TODO
 * @Author kehong
 * @Date 2020/11/20 9:04 上午
 * @Version 1.0
 **/
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public DataResult Handler(Exception e){
        return DataResult.getDataResult(BaseRespCode.SYS_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public DataResult Handler(CustomException e){
        return DataResult.getDataResult(e.getCode(),e.getMsg());
    }
}
