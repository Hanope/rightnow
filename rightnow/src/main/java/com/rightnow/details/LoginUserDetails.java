package com.rightnow.details;

import com.rightnow.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Created by HeemangHan on 2016. 11. 9..
 */
public class LoginUserDetails extends User {

    private static final long serialVersionUID = 1L;

    @Getter
    private Integer idx;

    public LoginUserDetails(Member member) {
        super(
                member.getId(),
                member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getRole()));

        idx = member.getIdx();
    }

}
