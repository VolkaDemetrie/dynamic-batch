package com.volka.dynamicbatch.batch.command;

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
public class AppContextProvider implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }

    static ApplicationContext getAppContext() {
        return appContext;
    }
}
