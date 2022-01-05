package com.mszlu.blog.handler;

import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 对加了@Controller注解的方法进行拦截处理AOP的实现
 *
 * @author by away
 * @date 2022/1/5 17:04
 */

@ControllerAdvice
public class AllExceptionHandler {

    /**
     * 进行异常处理，处理Exception.class的异常
     */

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception exception) {
        exception.printStackTrace();
        return  Result.fail(-999,"系统异常");
    }
}
