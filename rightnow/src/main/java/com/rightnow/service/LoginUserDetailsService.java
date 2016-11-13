package com.rightnow.service;

import com.rightnow.details.LoginUserDetails;
import com.rightnow.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by HeemangHan on 2016. 11. 9..
 */

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberService.getMember(id);

        if(member == null)
            throw new UsernameNotFoundException("Login fail");

        return new LoginUserDetails(member);
    }
}
