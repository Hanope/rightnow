package com.rightnow.domain;

import lombok.Data;

/**
 * Created by HeemangHan on 2016. 11. 26..
 */

@Data
public class DaySchedule {

    private Cafeteria breakfast;
    private Event morningEvent;
    private Cafeteria launch;
    private Tour launchTour;
    private Cafeteria dinner;
    private Accomodation accomodation;
}
