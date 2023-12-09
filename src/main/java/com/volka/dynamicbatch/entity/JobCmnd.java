package com.volka.dynamicbatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : job command 기준정보
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // JPA에서 lazy관련 에러 날 경우 사용
@Entity
@Table(name = "job_cmnd")
public class JobCmnd {
    @Id
    @Column(name = "job_cmnd")
    private String jobCmnd;
    @Column(name = "job_nm")
    private String jobNm;
    @Column(name = "job_desc")
    private String jobDesc;
    @Column(name = "reg_id")
    private String regId;
    @Column(name = "reg_dt")
    private String regDt;
    @Column(name = "mod_id")
    private String modId;
    @Column(name = "mod_dt")
    private String modDt;
}
