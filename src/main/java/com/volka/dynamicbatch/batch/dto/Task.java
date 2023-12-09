package com.volka.dynamicbatch.batch.dto;

import com.volka.dynamicbatch.entity.SchdJobCmndMapp;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : Job 작업정보
 */
@Getter
@Builder
public class Task {
    private final Integer schdCd;
    private final String schdCron;
    private final String schdNm;
    private final List<SchdJobCmndMapp> jobList;
}
