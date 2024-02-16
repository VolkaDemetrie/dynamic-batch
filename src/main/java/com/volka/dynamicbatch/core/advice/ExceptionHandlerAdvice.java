package com.volka.dynamicbatch.core.advice;

import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.config.exception.InvokeException;
import com.volka.dynamicbatch.core.dto.ResponseDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author         : volka <volka5091@gmail.com>
 * description    : REST 컨트롤러 예외처리 어드바이스
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = {BizException.class, Exception.class})
    public ResponseDTO<?> exceptionHandler(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            return ResponseDTO.response(bizException.getErrCd(), bizException.getErrMsg());
        } else if (e instanceof Exception) {
            return null;
        } else {
            return ResponseDTO.fail();
        }
    }

    @ExceptionHandler(InvokeException.class)
    public ResponseDTO<?> commandInvokeExceptionHandler(InvokeException e) {
        return null;
    }
}
