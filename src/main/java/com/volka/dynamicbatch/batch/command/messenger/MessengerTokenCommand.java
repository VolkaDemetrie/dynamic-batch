package com.volka.dynamicbatch.batch.command.messenger;

import com.volka.dynamicbatch.batch.AbstactCommand;
import com.volka.dynamicbatch.core.config.exception.InvokeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 메신저 GW 서버 토큰 업데이트
 *
 * @author volka
 */
@Slf4j
@Component
public class MessengerTokenCommand extends AbstactCommand {
    @Override
    protected void invoke() throws InvokeException, Exception {

    }

    @Override
    protected void backUp() {

    }
}
