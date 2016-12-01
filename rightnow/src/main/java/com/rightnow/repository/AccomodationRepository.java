package com.rightnow.repository;

import com.rightnow.domain.Accomodation;
import com.rightnow.domain.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */
public interface AccomodationRepository extends JpaRepository<Accomodation, Integer> {

    @Query(value = "SELECT * FROM accomodation a WHERE a.price BETWEEN (:price*0.7) AND :price ORDER BY a.rating DESC, RAND() LIMIT 1", nativeQuery = true)
    public Accomodation find(@Param("price") int price);
}
