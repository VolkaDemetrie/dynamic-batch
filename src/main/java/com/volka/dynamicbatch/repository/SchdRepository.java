package com.volka.dynamicbatch.repository;

import com.volka.dynamicbatch.entity.Schd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : volka <volka5091@gmail.com>
 * description    :
 */
@Repository
public interface SchdRepository extends JpaRepository<Schd, Integer> {
}
