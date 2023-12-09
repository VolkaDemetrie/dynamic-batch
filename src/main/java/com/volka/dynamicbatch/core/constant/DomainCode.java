package com.volka.dynamicbatch.core.constant;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 도메인 코드 상수
 */
public enum DomainCode {

    RUN_HST("RUN", "job 실행이력"),
    ;

    private final String code;
    private final String desc;

    DomainCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
