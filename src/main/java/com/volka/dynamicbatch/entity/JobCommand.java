package com.volka.dynamicbatch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : job command 기준정보
 */
@Data
@NoArgsConstructor
public class JobCommand {
    private String jobCommand;
    private String jobNm;
    private String jobDesc;
    private String regId;
    private String regDt;
    private String modId;
    private String modDt;
}
