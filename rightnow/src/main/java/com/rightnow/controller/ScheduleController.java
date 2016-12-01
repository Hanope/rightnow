package com.rightnow.controller;

import com.rightnow.domain.ScheduleCondition;
import com.rightnow.service.ScheduleService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.joda.time.*;
import org.joda.time.format.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value = "make", method = RequestMethod.GET)
    public ArrayList<Object> makeSchedule(HttpServletRequest request) {
        String location = request.getParameter("location");
        int money = Integer.parseInt(request.getParameter("money"));
        String startDate = request.getParameter("startDate").replace("T", " ");
        String endDate = request.getParameter("endDate").replace("T", " ");
        String theme = request.getParameter("theme");

        DateTimeFormatter sDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter eDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime sDate = sDateFormat.parseDateTime(startDate);
        DateTime eDate = eDateFormat.parseDateTime(endDate);

        ScheduleCondition scheduleCondition = new ScheduleCondition(location, money, sDate, eDate, theme);

        return scheduleService.makePlan(scheduleCondition);
    }
}
