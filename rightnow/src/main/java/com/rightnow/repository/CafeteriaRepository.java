package com.rightnow.repository;

import com.rightnow.domain.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */
public interface CafeteriaRepository extends JpaRepository<Cafeteria, Integer> {

    @Query(value = "SELECT * FROM cafeteria c WHERE c.price <= :price ORDER BY c.rating DESC, RAND() LIMIT 1", nativeQuery = true)
    public Cafeteria find(@Param("price") int price);
}
