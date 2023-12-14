package com.volka.dynamicbatch.entity.compositekey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : schd : job 매핑 복합키
 */
@Data
@Embeddable
public class SchdJobMappKey implements Serializable {
    @Column(name = "schd_cd")
    private int schdCd;

    @Column(name = "job_cmnd")
    private String jobCmnd;
}
