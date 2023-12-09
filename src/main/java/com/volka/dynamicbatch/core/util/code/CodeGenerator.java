package com.volka.dynamicbatch.core.util.code;

import com.volka.dynamicbatch.core.constant.DomainCode;
import com.volka.dynamicbatch.repository.RunHstRepository;

import java.time.LocalDateTime;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 코드 생성기 :: 싱글턴에 가깝긴 하나 전역접근을 제공하진 않고 CodeUtil을 통해서만 호출하게 한다.
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
