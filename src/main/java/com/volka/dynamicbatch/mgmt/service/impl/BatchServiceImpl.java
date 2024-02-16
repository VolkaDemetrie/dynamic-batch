package com.volka.dynamicbatch.mgmt.service.impl;

import com.volka.dynamicbatch.batch.DynamicBatch;
import com.volka.dynamicbatch.core.config.exception.BizException;
import com.volka.dynamicbatch.core.constant.ResultCode;
import com.volka.dynamicbatch.mgmt.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 배치 관리 서비스 구현
 *
 * @author volka
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BatchServiceImpl implements BatchService {

    private final DynamicBatch dynamicBatch;


    /**
     * 배치 초기화
     *
     * @return
     */
    @Transactional(rollbackFor = {BizException.class, Exception.class})
    @Override
    public ResultCode init() {
        try {
            dynamicBatch.batchInit();
            return ResultCode.SUCCESS;

        } catch (BizException e) {
            log.error("BATCH INIT ERROR :: {} : {}", e.getErrCd(), e.getErrMsg());
            throw e;
        } catch (Exception e) {
            log.error("BATCH INIT ERROR :: {} : {}", e.getLocalizedMessage(), e.toString());
            throw new BizException("BTE00001", e);
        }
    }
}
