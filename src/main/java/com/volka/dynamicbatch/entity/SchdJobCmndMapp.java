package com.volka.dynamicbatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.volka.dynamicbatch.entity.compositekey.SchdJobMappKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 스케줄 : job 커맨드 매핑
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // JPA에서 lazy관련 에러 날 경우 사용
@Entity
@Table(name = "schd_job_cmnd_mapp")
public class SchdJobCmndMapp {

    @EmbeddedId
    private SchdJobMappKey mappKey;

    @Column(name = "step")
    private int step;
    @Column(name = "retry")
    private int retry;
    @Column(name = "reg_id")
    private String regId;
    @Column(name = "reg_dt")
    private String regDt;
    @Column(name = "mod_id")
    private String modId;
    @Column(name = "mod_dt")
    private String modDt;
}
