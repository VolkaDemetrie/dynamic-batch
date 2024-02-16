package com.volka.dynamicbatch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 실행이력 Entity
 */
@Data
@NoArgsConstructor
public class JobRunHst {

    private String jobRunHstId;
    private String jobCommand;
    private String succYn;
    private String errCd;
    private String errMsg;
    private String regDt;
}
