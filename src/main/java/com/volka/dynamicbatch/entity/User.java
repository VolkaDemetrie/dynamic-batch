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
 * description    : 사용자 정보
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // JPA에서 lazy관련 에러 날 경우 사용
@Entity  // 객체와 테이블 매핑
@Table(name = "user")  // 테이블 지정
public class User {
    @Id  // Primary Key 지정
//    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 설정 (id값이 null일 경우 자동 생성)
    @Column(name = "id")  // 컬럼 지정
    private String id;
    @Column(name = "pw")  // 컬럼 지정
    private String pw;
    @Column(name = "reg_id")  // 컬럼 지정
    private String regId;
    @Column(name = "reg_dt")  // 컬럼 지정
    private String regDt;
    @Column(name = "mod_id")  // 컬럼 지정
    private String modId;
    @Column(name = "mod_dt")  // 컬럼 지정
    private String modDt;
}
