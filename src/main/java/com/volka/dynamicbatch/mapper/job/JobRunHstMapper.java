package com.volka.dynamicbatch.mapper.job;

import com.volka.dynamicbatch.entity.JobRunHst;
import org.apache.ibatis.annotations.Mapper;

/**
 * Job Command 실행이력 mapper
 *
 * @author volka
 */
@Mapper
public interface JobRunHstMapper {

    /**
     * 시퀀스 채번
     * @return
     */
    int selectSeq();

    /**
     * 실행이력 적재
     * @param jobRunHst
     */
    void insert(JobRunHst jobRunHst);

    /**
     * 실행결과 업데이트
     * @param jobRunHst
     */
    void update(JobRunHst jobRunHst);
}
