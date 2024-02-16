package com.volka.dynamicbatch.batch;

import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.config.exception.InvokeException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 커맨드 실행기
 */
@Slf4j
public class CommandInvoker {

    private CommandInvoker() {
        super();
    }


    public static void invoke(AbstactCommand command) throws BizException {
        try {
            ++command.tryCnt;
            command.invoke();

            command.tryCnt = 0; //성공 시 시도횟수 초기화

        } catch (InvokeException e) {
            log.error("invoke error :: {} :: {}", e.getErrCd(), e.getErrMsg());
            if (e.getCommand().maxRetry == 0) {
                throw new BizException("BTE00000"); //배치 실패
            } else {
                retry(e.getCommand());
            }
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
            } else {
                throw new BizException("BTE00000"); //배치 실패
            }
        } catch (BizException e) {
            log.error("invoker retry error :: {} :: {}", e.getErrCd(), e.getErrMsg());
            throw e;
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
