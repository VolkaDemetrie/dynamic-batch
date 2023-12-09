package com.volka.dynamicbatch.core.config.dto;

import com.volka.dynamicbatch.core.constant.ResultCode;
import lombok.Getter;

/**
 * @author : volka <volka5091@gmail.com>
 * description    :
 */
@Getter
public class ResponseDTO<T> {
    private final String code;
    private final String msg;
    private T content;

    public static <T> ResponseDTO<T> success() {
        return new ResponseDTO<>(ResultCode.SUCCESS);
    }

    public static <T> ResponseDTO<T> fail() {
        return new ResponseDTO<>(ResultCode.FAIL);
    }

    public static <T> ResponseDTO<T> response(ResultCode resultCode) {
        return new ResponseDTO<>(resultCode);
    }

    public static <T> ResponseDTO<T> response(String errCd, String errMsg) {
        return new ResponseDTO<>(errCd, errMsg);
    }

    public static <T> ResponseDTO<T> response(T content) {
        return new ResponseDTO<>(ResultCode.SUCCESS, content);
    }


    private ResponseDTO(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    private ResponseDTO(ResultCode resultCode, T content) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.content = content;
    }

    private ResponseDTO(String errCd, String errMsg) {
        this.code = errCd;
        this.msg = errMsg;
    }
}
