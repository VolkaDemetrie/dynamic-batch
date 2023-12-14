package com.volka.dynamicbatch.core.util.code;

import com.volka.dynamicbatch.core.constant.DomainCode;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 코드 생성기 :: 완벽한 싱글턴은 아니다(전역접근 제공안함) CodeUtil을 통해서만 호출하게 한다.
 */
public class CodeGenerator {
    private static CodeGenerator instance;

    private CodeGenerator() {
        super();
    }

    static synchronized CodeGenerator getInstance() {
        if (instance == null) instance = new CodeGenerator();
        return instance;
    }

    String generateSysId(DomainCode domainCode, int seq) {
        return String.format("%s%s%04d", domainCode.getCode(), System.currentTimeMillis(), seq);
    }
}
