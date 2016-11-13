package com.rightnow.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by HeemangHan on 2016. 11. 9..
 */

@Data
public class BoardPost {

    private Integer idx;
    private String author;
    private String title;
    private String content;
    private MultipartFile uploadfile;
}
