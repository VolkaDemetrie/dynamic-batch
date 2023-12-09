package com.volka.dynamicbatch.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 스케줄 기준정보
 */
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // JPA에서 lazy관련 에러 날 경우 사용
@Entity
@Table(name = "schd")
public class Schd {
    @Id
    @Column(name = "schd_cd")
    private int schdCd;
    @Column(name = "schd_cron")
    private String schdCron;
    @Column(name = "schd_nm")
    private String schdNm;
    @Column(name = "reg_id")
    private String regId;
    @Column(name = "reg_dt")
    private String regDt;
    @Column(name = "mod_id")
    private String modId;
    @Column(name = "mod_dt")
    private String modDt;
}
