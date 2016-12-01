package com.rightnow.service;

import com.rightnow.domain.*;
import com.rightnow.repository.AccomodationRepository;
import com.rightnow.repository.CafeteriaRepository;
import com.rightnow.repository.EventRepository;
import com.rightnow.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */

@Service
public class ScheduleService {

    @Autowired
    CafeteriaRepository cafeteriaRepository;

    @Autowired
    AccomodationRepository accomodationRepository;

    @Autowired
    TourRepository tourRepository;

    @Autowired
    EventRepository eventRepository;

    public ArrayList<Object> makePlan(ScheduleCondition scheduleCondition) {
        ArrayList<Object> schedule = new ArrayList<>();
        int totalMoney = scheduleCondition.getMoney();
        int departTime = scheduleCondition.getStartDate().getHourOfDay();
        int numOfSleep = scheduleCondition.getEndDate().getDayOfMonth() - scheduleCondition.getStartDate().getDayOfMonth();
        int numOfEat = numOfSleep > 0 ? (numOfSleep + 1) * 3 - 1 : 3;
        int numOfTour = (numOfSleep + 1);
        int numOfEvent = (numOfSleep + 1);
        String theme = scheduleCondition.getTheme();

        if (departTime > 7) {
            numOfEat--;

            if (departTime >= 12)
                numOfEat--;

            if (departTime >= 18) {
                numOfEat--;
                numOfTour--;
            }
        }

        ScheduleWeight scheduleWeight = new ScheduleWeight(numOfEat, numOfSleep,
                numOfTour,theme , totalMoney);

        Map<String, Object> day = new LinkedHashMap<>();
        day.put("day", "1Day");
        day.put("breakfast", getCafeteria(scheduleWeight.getFoodMoney()));
        day.put("event", getEvent(scheduleWeight.getTourMoney()));
        day.put("lunch", getCafeteria(scheduleWeight.getFoodMoney()));
        day.put("tour", getTour(scheduleWeight.getTourMoney()));
        day.put("dinner", getCafeteria(scheduleWeight.getFoodMoney()));

        if(numOfSleep != 0)
            day.put("accommodation", getAccomodation(scheduleWeight.getAccomoMoney()));
        else
            day.put("accommodation", null);

        if (departTime > 7) {
            day.put("breakfast", null);

            if (departTime > 11)
                day.put("event", null);
            if (departTime >= 12)
                day.put("lunch", null);
            if (departTime >= 18) {
                day.put("dinner", null);
                day.put("tour", null);
            }
        }

        schedule.add(day);

        for(int i=1; i<=numOfSleep; i++) {
            String tourDay = (i+1) + "Day";
            day = new LinkedHashMap<>();

            day.put("day", tourDay);
            day.put("breakfast", getCafeteria(scheduleWeight.getFoodMoney()));
            day.put("event", getEvent(scheduleWeight.getTourMoney()));
            day.put("lunch", getCafeteria(scheduleWeight.getFoodMoney()));
            day.put("tour", getTour(scheduleWeight.getTourMoney()));
            day.put("dinner", getCafeteria(scheduleWeight.getFoodMoney()));
            day.put("accommodation", getAccomodation(scheduleWeight.getAccomoMoney()));

            if(i == numOfSleep) {
                day.put("dinner", null);
                day.put("accommodation", null);
            }

            schedule.add(day);
        }


        return schedule;
    }

    private Cafeteria getCafeteria(int money) {
        Cafeteria cafeteria = null;

        while (true) {
            cafeteria = cafeteriaRepository.find(money);

            if (cafeteria == null)
                money += 10000;
            else
                break;
        }

        return cafeteria;
    }

    private Accomodation getAccomodation(int money) {
        Accomodation accomodation = null;

        while (true) {
            accomodation = accomodationRepository.find(money);

            if (accomodation == null)
                money += 10000;
            else
                break;
        }

        return accomodation;
    }

    private Tour getTour(int money) {
        Tour tour = null;

        while (true) {
            tour = tourRepository.find(money);

            if (tour == null)
                money += 10000;
            else
                break;
        }

        return tour;
    }

    private Event getEvent(int money) {
        Event event = null;

        while (true) {
            event = eventRepository.find(money);

            if (event == null)
                money += 10000;
            else
                break;
        }

        return event;
    }
}
