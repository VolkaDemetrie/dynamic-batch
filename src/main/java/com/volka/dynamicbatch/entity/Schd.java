package com.volka.dynamicbatch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 스케줄 기준정보
 */
@Data
@NoArgsConstructor
public class Schd {
    private Integer schdCd;
    private String schdCron;
    private String schdNm;
    private String regId;
    private String regDt;
    private String modId;
    private String modDt;
}
