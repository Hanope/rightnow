package com.rightnow.repository;

import com.rightnow.domain.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by HeemangHan on 2016. 11. 10..
 */
public interface ImageFileRepository extends JpaRepository<ImageFile, Integer> {
}
