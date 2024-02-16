package com.volka.dynamicbatch.core.config.exception;

import com.volka.dynamicbatch.batch.AbstactCommand;
import com.volka.dynamicbatch.core.constant.ResultCode;
import lombok.Getter;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 커맨드 실행 예외
 */
@Getter
public class InvokeException extends RuntimeException {
    private final String errCd;
    private final String errMsg;
    private final AbstactCommand command;

    public InvokeException(BizException e, AbstactCommand command) {
        this.errCd = e.getErrCd();
        this.errMsg = e.getErrMsg();
        this.command = command;
    }

    public InvokeException(Throwable t, AbstactCommand command) {
        super(t);
        this.errCd = ResultCode.FAIL.getCode();
        this.errMsg = t.getLocalizedMessage();
        this.command = command;
    }
}
