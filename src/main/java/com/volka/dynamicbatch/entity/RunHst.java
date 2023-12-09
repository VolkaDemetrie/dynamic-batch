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
 * description    :
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // JPA에서 lazy관련 에러 날 경우 사용
@Entity
@Table(name = "run_hst")
public class RunHst {

    @Id
    @Column(name = "run_hst_id")
    private String runHstId;
    @Column(name = "job_cmnd")
    private String jobCmnd;
    @Column(name = "err_cd")
    private String errCd;
    @Column(name = "err_msg")
    private String errMsg;
    @Column(name = "reg_dt")
    private String regDt;
}
