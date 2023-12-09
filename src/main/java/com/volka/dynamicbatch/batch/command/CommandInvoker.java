package com.volka.dynamicbatch.batch.command;

import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.config.exception.InvokeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 커맨드 실행기
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CommandInvoker {
    private static final ApplicationContext appContext = AppContextProvider.getAppContext();

    public static void invoke(String commandNm) {
        try {
            AbstactCommand command = (AbstactCommand) appContext.getBean(commandNm);
            ++command.tryCnt;
            command.invoke();
        } catch (BizException e) {
            log.error("invoke error :: {} :: {}", e.getErrCd(), e.getErrMsg());
        } catch (InvokeException e) {
            log.error("invoke error :: {} :: {}", e.getErrCd(), e.getErrMsg());
            retry(e.getCommand());
        } catch (Exception e) {
            log.error("invoke error :: {} :: {}", e.getLocalizedMessage(), e.toString());
        }
    }

    /**
     * 재실행, 실행횟수 검증
     *
     * @param command
     */
    private static void retry(AbstactCommand command) {
        try {
            if (command.maxRetry > command.tryCnt) {
                ++command.tryCnt;
                command.invoke();
            }
        } catch (BizException e) {
            log.error("invoker retry error :: {} :: {}", e.getErrCd(), e.getErrMsg());
        } catch (InvokeException e) {
            log.error("invoker retry error :: {} :: {}", e.getErrCd(), e.getErrMsg());
            retry(e.getCommand());
        } catch (Exception e) {
            log.error("invoker retry error :: {} :: {}", e.getLocalizedMessage(), e.toString());
        }
    }

    private void backUp() {

    }

    private void undo() {

    }
}
