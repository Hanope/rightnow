package com.rightnow.repository;

import com.rightnow.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by HeemangHan on 2016. 11. 7..
 */
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
