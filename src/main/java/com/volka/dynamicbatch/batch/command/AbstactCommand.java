package com.volka.dynamicbatch.batch.command;

import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.config.exception.InvokeException;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 추상커맨드
 */
public abstract class AbstactCommand {
    protected int maxRetry; //최대 재실행 횟수
    protected int tryCnt; //현재 실행 카운트
//    protected AbstactCommand command;

    protected abstract void invoke() throws BizException, InvokeException, Exception;

    protected abstract void backUp();

}
