package com.rightnow.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by HeemangHan on 2016. 11. 7..
 */

@Entity
@Data
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idx;

    @Column
    private String content;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;

//    @ManyToMany
//    @JsonBackReference
//    private Member member;

//    @ManyToMany
//    @JsonBackReference
//    private Board board;

    public Reply() { }
}