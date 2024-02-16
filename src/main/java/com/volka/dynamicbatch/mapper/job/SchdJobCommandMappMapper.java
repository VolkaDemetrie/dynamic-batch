package com.volka.dynamicbatch.mapper.job;

import com.volka.dynamicbatch.entity.SchdJobCommandMapp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 스케줄 코드 : job 커맨드 매핑 및 순서, 재시도 기준정보 mapper
 *
 * @author volka
 */
@Mapper
public interface SchdJobCommandMappMapper {

    /**
     * mapping 전체 조회
     *
     * @return
     */
    List<SchdJobCommandMapp> selectAll();
}
