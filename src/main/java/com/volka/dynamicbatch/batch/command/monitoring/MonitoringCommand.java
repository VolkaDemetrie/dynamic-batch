package com.volka.dynamicbatch.batch.command.monitoring;

import com.volka.dynamicbatch.batch.AbstactCommand;
import com.volka.dynamicbatch.core.config.exception.InvokeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author : volka <volka5091@gmail.com>
 * description    :
 */
@Slf4j
@Component
public class MonitoringCommand extends AbstactCommand {
    @Override
    protected void invoke() throws InvokeException, Exception {
        log.info("=== monitoring invoke ===");
    }

    @Override
    protected void backUp() {

    }
}
