package com.volka.dynamicbatch.mgmt.controller;

import com.volka.dynamicbatch.core.constant.ResultCode;
import com.volka.dynamicbatch.core.dto.ResponseDTO;
import com.volka.dynamicbatch.mgmt.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/mgmt/batch")
@RestController
public class BatchController {

    private final BatchService batchService;

    @PatchMapping("/init")
    public ResponseDTO<ResultCode> batchInit() {
        return ResponseDTO.response(batchService.init());
    }
}
