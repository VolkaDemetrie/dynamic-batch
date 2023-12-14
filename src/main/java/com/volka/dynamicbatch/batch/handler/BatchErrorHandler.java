package com.volka.dynamicbatch.batch.handler;

import com.volka.dynamicbatch.core.util.CommunicateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 배치 error 핸들러
 */
@RequiredArgsConstructor
@Component
public class BatchErrorHandler implements ErrorHandler {

    private final CommunicateUtil communicateUtil;

    @Override
    public void handleError(Throwable t) {
        //TODO : file beats로 elk 연동
    }
}
