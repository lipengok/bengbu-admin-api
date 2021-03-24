package com.guli.common.handler;

/**
 * 统一异常处理类
 */
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    //统一错误处理方法
    public R error(Exception e){
        e.printStackTrace();
        return R.error();
    }
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    //指定错误处理方法
    public R error(BadSqlGrammarException e){
        e.printStackTrace();
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    //指定错误处理方法
    public R error(HttpMessageNotReadableException e){
        e.printStackTrace();
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(GuliException.class)
    @ResponseBody
    //自定义错误处理方法
    public R error(GuliException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
