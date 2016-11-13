package com.rightnow.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by HeemangHan on 2016. 11. 7..
 */

@Entity
@Data
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idx;

    @Column
    private String src;

    @ManyToOne
    @JsonBackReference
    private Board board;

    public ImageFile() { }
}