package com.volka.dynamicbatch.batch.dto;

import com.volka.dynamicbatch.entity.SchdJobCmndMapp;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : Job 작업정보
 */
@Data
public class Task {
    private int schdCd;
    private String schdCron;
    private List<SchdJobCmndMapp> jobList;
}
