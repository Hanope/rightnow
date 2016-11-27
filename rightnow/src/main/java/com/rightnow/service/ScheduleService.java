package com.rightnow.service;

import com.rightnow.domain.*;
import com.rightnow.repository.AccomodationRepository;
import com.rightnow.repository.CafeteriaRepository;
import com.rightnow.repository.EventRepository;
import com.rightnow.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public Map<String, Object> makePlan(ScheduleCondition scheduleCondition) {
        Map<String, Object> plan = new HashMap<>();
        int totalMoney = scheduleCondition.getMoney();
        int balance = totalMoney;
        int departTime = scheduleCondition.getStartDate().getHourOfDay();
        int numOfSleep = scheduleCondition.getEndDate().getDayOfMonth() - scheduleCondition.getStartDate().getDayOfMonth();
        int numOfEat = (numOfSleep + 1) * 3 - 1;
        int numOfTour = (numOfSleep +1) * 2;

        boolean eatFirst = true;

        if(departTime > 7 && departTime < 12) {
            numOfEat -= 1;
        } else if(departTime >= 12 && departTime < 18) {
            numOfEat -= 2;
        } else if(departTime >= 18 && departTime < 24) {
            numOfEat -= 3;
        }

        ScheduleWeight scheduleWeight = new ScheduleWeight(numOfEat, numOfSleep,
                numOfTour, "eat", totalMoney);

        Accomodation accomodation[] = new Accomodation[numOfSleep];
        Cafeteria cafeteria[] = new Cafeteria[numOfEat];
        Tour tour[] = new Tour[numOfTour/2];
        Event event[] = new Event[numOfTour/2];

        for(int i=0; i<numOfEat; i++) {
            cafeteria[i] = getCafeteria(scheduleWeight.getFoodMoney());
            balance -= cafeteria[i].getPrice();
        }

        for(int i=0; i<numOfSleep; i++) {
            accomodation[i] = getAccomodation(scheduleWeight.getAccomoMoney());
            balance -= accomodation[i].getPrice();
        }

        for(int i=0; i<numOfTour/2; i++) {
            tour[i] = getTour(scheduleWeight.getTourMoney());
            event[i] = getEvent(scheduleWeight.getTourMoney());

            balance -= (tour[i].getPrice() + event[i].getPrice());
        }

        plan.put("food", cafeteria);
        plan.put("tour", tour);
        plan.put("event", event);
        plan.put("sleep", accomodation);
        plan.put("totalCost", totalMoney - balance);

        return plan;
    }

    private Cafeteria getCafeteria(int money) {
        Cafeteria cafeteria = null;

        while(true) {
            cafeteria = cafeteriaRepository.find(money);

            if(cafeteria == null)
                money += 10000;
            else
                break;
        }

        return cafeteria;
    }

    private Accomodation getAccomodation(int money) {
        Accomodation accomodation = null;

        while(true) {
            accomodation = accomodationRepository.find(money);

            if(accomodation == null)
                money += 10000;
            else
                break;
        }

        return accomodation;
    }

    private Tour getTour(int money) {
        Tour tour = null;

        while(true) {
            tour = tourRepository.find(money);

            if(tour == null)
                money += 10000;
            else
                break;
        }

        return tour;
    }

    private Event getEvent(int money) {
        Event event = null;

        while(true) {
            event = eventRepository.find(money);

            if(event == null)
                money += 10000;
            else
                break;
        }

        return event;
    }
}
