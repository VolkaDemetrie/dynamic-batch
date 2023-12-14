package com.volka.dynamicbatch.core.util;

import com.volka.dynamicbatch.core.config.communicate.WebClientConfig;
import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.dto.communicate.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 통신유틸
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class CommunicateUtil {

    private final WebClientConfig webClient;


    public boolean notify(Sender sender) {
        try {
            return false;
        } catch (BizException e) {
            log.error("notify ERROR :: {} : {}", e.getErrCd());
            return false;
        } catch (Exception e) {
            log.error("notify ERROR :: {} : {}", e.getLocalizedMessage(), e.toString());
            return false;
        }
    }
}
