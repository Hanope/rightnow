package com.rightnow.domain;

import lombok.Data;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */

@Data
public class ScheduleWeight {

    private int totalWeight;
    private int foodWeight;
    private int accomoWeight;
    private int tourWeight;
    private int totalFoodMoney;
    private int totalAccomoMoney;
    private int totalTourMoney;
    private int foodMoney;
    private int accomoMoney;
    private int tourMoney;
    
    public ScheduleWeight(int numOfEat, int numOfSleep, int numOfTour, String theme, int money) {
        foodWeight = numOfEat * 2;
        accomoWeight = numOfSleep * 6;
        tourWeight = numOfTour;

        if(theme.equals("eat"))
            foodWeight *= 1.5;
        else if(theme.equals(("sleep")))
            accomoWeight *= 1.5;

        totalWeight = foodWeight + accomoWeight + tourWeight;
        
        totalFoodMoney = (int)(foodWeight / (double)totalWeight * money);
        totalAccomoMoney = (int)(accomoWeight / (double)totalWeight * money);
        totalTourMoney = (int)(tourWeight / (double)totalWeight * money);
        
        foodMoney = totalFoodMoney / numOfEat;
        accomoMoney = totalAccomoMoney / numOfSleep;
        tourMoney = totalTourMoney / numOfTour;
    }
}
