package com.rightnow.repository;

import com.rightnow.domain.Cafeteria;
import com.rightnow.domain.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */
public interface TourRepository extends JpaRepository<Tour, Integer> {

    @Query(value = "SELECT * FROM tour t WHERE t.price <= :price ORDER BY t.rating DESC, RAND() LIMIT 1", nativeQuery = true)
    public Tour find(@Param("price") int price);
}
