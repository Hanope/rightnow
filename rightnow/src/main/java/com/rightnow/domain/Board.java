package com.rightnow.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by HeemangHan on 2016. 11. 7..
 */

@Entity
@Data
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idx;

    @Column
    private String title;

    @Column
    private String content;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp date;

    @ManyToOne
    private Member member;

    @OneToMany
    @JoinColumn(name="board_idx")
    private List<ImageFile> imageFileList;


    public Board() { }

    public Board(BoardPost boardPost, Member member) {
        this.title = boardPost.getTitle();
        this.content = boardPost.getContent();
        this.member = member;
    }
}
