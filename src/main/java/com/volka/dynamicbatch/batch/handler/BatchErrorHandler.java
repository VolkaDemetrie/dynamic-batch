package com.volka.dynamicbatch.batch.handler;

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


    @Override
    public void handleError(Throwable t) {

    }
}
