package com.rightnow.controller;

import com.rightnow.domain.Member;
import com.rightnow.repository.MemberRepository;
import com.rightnow.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HeemangHan on 2016. 11. 11..
 */

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Map<String, String> joinProcessing(Member member) {
        Map<String, String> json = new HashMap<>();
        String result, message;

        if(memberService.create(member)) {
            result = "success";
            message = "회원가입이 완료되었습니다.";
        } else {
            result = "failed";
            message = "회원가입을 실패하였습니다.";
        }

        json.put("result", result);
        json.put("message", message);

        return json;
    }

    @RequestMapping(value = "/idCheck", method = RequestMethod.GET)
    public Map<String, String> idCheck(@RequestParam(value = "memberId") String id) {
        Map<String, String> json = new HashMap<>();
        String result, message;

        Member member = memberRepository.findOneById(id);

        if(member == null) {
            result = "success";
            message = "사용할 수 있는 아이디입니다.";
        }
        else {
            result = "failed";
            message = "이미 존재하는 아이디입니다.";
        }

        json.put("result", result);
        json.put("message", message);

        return json;
    }
}
