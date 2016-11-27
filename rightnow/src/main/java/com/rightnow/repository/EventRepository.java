package com.rightnow.repository;

import com.rightnow.domain.Cafeteria;
import com.rightnow.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query(value = "SELECT * FROM event e WHERE e.price <= :price ORDER BY e.rating DESC, RAND() LIMIT 1", nativeQuery = true)
    public Event find(@Param("price") int price);
}
