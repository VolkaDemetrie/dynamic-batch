package com.volka.dynamicbatch.core.constant;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 결과코드
 */
public enum ResultCode {

    SUCCESS("0000", "성공"),
    FAIL("9999", "실패"),
    ;

    private final String code;
    private final String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
