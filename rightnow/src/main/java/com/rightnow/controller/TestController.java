package com.rightnow.controller;

import com.rightnow.details.LoginUserDetails;
import com.rightnow.domain.Board;
import com.rightnow.domain.BoardPost;
import com.rightnow.domain.Member;
import com.rightnow.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by HeemangHan on 2016. 11. 8..
 */

@Controller
public class TestController {

    @RequestMapping("/")
    String main() {
        return "index";
    }

    @RequestMapping("/main")
    String boardList() {
        return "board";
    }

    @RequestMapping("/board/write")
    public String boardWrite() {
        return "write";
    }
}
