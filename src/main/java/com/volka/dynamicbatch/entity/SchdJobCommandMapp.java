package com.volka.dynamicbatch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 스케줄 : job 커맨드 매핑 Entity
 */
@Data
@NoArgsConstructor
public class SchdJobCommandMapp {

    private int schdCd;
    private String jobCommand;
    private int step;
    private int retry;
    private String regId;
    private String regDt;
    private String modId;
    private String modDt;
}
