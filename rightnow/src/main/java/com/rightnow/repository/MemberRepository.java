package com.rightnow.repository;

import com.rightnow.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by HeemangHan on 2016. 11. 9..
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {

    public Member findOneById(String id);

    public Member findOneByIdx(Integer idx);
}
