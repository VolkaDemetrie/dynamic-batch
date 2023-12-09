package com.volka.dynamicbatch.core.util;

import com.volka.dynamicbatch.core.config.communicate.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 통신유틸
 */
@RequiredArgsConstructor
@Component
public class CommunicateUtil {

    private final WebClientConfig webClient;
}
