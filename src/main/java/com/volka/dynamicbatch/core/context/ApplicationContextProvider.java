package com.volka.dynamicbatch.core.context;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 앱 컨텍스트 provider
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    public ApplicationContext getAppContext() {
        return appContext;
    }
}
