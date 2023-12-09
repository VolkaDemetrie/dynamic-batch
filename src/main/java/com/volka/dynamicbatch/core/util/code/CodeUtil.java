package com.volka.dynamicbatch.core.util.code;

import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.constant.DomainCode;
import com.volka.dynamicbatch.repository.RunHstRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 코드 유틸
 */
@RequiredArgsConstructor
@Component
public class CodeUtil {

    private static final CodeGenerator cdGenerator = CodeGenerator.getInstance();
    private final RunHstRepository runHstRepository;

    /**
     * 시스템 아이디 생성
     *
     * @param domainCode
     * @return
     * @throws BizException
     * @throws Exception
     */
    public String makeSysId(DomainCode domainCode) throws BizException, Exception {
        String sysId = null;

        switch (domainCode) {
            case RUN_HST:
                sysId = cdGenerator.generateSysId(domainCode, runHstRepository.getSeq());
                break;
            default:
                throw new BizException(""); //TODO : undefined code 관련 에러코드 정의
        }

        return sysId;
    }
}
