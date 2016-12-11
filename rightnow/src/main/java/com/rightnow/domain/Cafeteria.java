package com.rightnow.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by HeemangHan on 2016. 11. 24..
 */

@Entity
@Data
public class Cafeteria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    @Column
    private String address;

    @Column
    private int areacode;

    @Column
    private int typecode;

    @Column
    private String image;

    @Column
    private Double longitude;

    @Column
    private Double latitude;

    @Column
    private String tel;

    @Column
    private String name;

    @Column
    private int price;

    @Column
    private int rating;
}
