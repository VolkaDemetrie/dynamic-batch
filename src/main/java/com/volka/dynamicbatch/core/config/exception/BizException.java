package com.volka.dynamicbatch.core.config.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 런타임 예외
 */
@Getter
public class BizException extends RuntimeException {

    private String errCd;
    private String errMsg;

    public BizException(String errCd) {
        this.errCd = errCd;
    }
    public BizException(String errCd, String errMsg) {
        this.errCd = errCd;
        this.errMsg = errMsg;
    }
    public BizException(String errCd, Throwable throwable) {
        this.errCd = errCd;
        this.errMsg = throwable.getLocalizedMessage();
    }
}
