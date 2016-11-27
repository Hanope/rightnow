package com.rightnow.domain;

import lombok.Data;
import org.joda.time.DateTime;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */

@Data
public class ScheduleCondition {

    private String location;

    private int money;

    private DateTime startDate;

    private DateTime endDate;

    private String theme;

    public ScheduleCondition() {}

    public ScheduleCondition(String location, int money, DateTime startDate, DateTime endDate, String theme) {
        this.location = location;
        this.money = money;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theme = theme;
    }
}