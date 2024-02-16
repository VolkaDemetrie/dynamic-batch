package com.volka.dynamicbatch.dto;

import lombok.Builder;
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
    private final List<CommandInfo> jobList;
}
