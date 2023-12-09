package com.volka.dynamicbatch.repository;

import com.volka.dynamicbatch.entity.RunHst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author : volka <volka5091@gmail.com>
 * description    : 배치 잡 실행이력 레포지토리
 */
@Repository
public interface RunHstRepository extends JpaRepository<RunHst, String> {
    @Query(value = "SELECT nextval('seq_run_hst')", nativeQuery = true)
    int getSeq();
}
