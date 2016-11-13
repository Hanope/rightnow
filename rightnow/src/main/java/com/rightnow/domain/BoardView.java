package com.rightnow.domain;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by HeemangHan on 2016. 11. 13..
 */

@Data
public class BoardView {

    private Integer idx;

    private String title;

    private String content;

    private Timestamp date;

    private String author;

    private List<ImageFile> imageFileList;

    public BoardView(Board board) {
        this.idx = board.getIdx();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.date = board.getDate();
        this.author = board.getMember().getName();
        this.imageFileList = board.getImageFileList();
    }
}
