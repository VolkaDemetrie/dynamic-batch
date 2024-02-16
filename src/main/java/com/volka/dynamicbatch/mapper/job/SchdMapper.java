package com.volka.dynamicbatch.mapper.job;

import com.volka.dynamicbatch.entity.Schd;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 스케줄 기준정보 mapper
 *
 * @author volka
 */
@Mapper
public interface SchdMapper {

    List<Schd> selectAll();
}
