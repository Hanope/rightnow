package com.rightnow.service;

import com.rightnow.domain.*;
import com.rightnow.repository.BoardRepository;
import com.rightnow.repository.ImageFileRepository;
import com.rightnow.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by HeemangHan on 2016. 11. 9..
 */

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ImageFileRepository imageFileRepository;

    public List<BoardView> view(Page<Board> boardPage) {
        List<Board> boardList = boardPage.getContent();
        List<BoardView> boardViewList = new ArrayList<>();

        for(Board board : boardList) {
            BoardView boardView =  new BoardView(board);
            boardViewList.add(boardView);
        }

        return boardViewList;
    }

    public BoardView view(Board board) {
        BoardView boardView = new BoardView(board);

        return boardView;
    }

    @Transactional
    public BoardView post(BoardPost boardPost, int memberIdx) {
        Member member = memberRepository.findOneByIdx(memberIdx);
        String fileName = null;

        Board board = new Board(boardPost, member);
        board = boardRepository.save(board);

        if(!boardPost.getUploadfile().getOriginalFilename().equals(""))
            fileName = uploadFile(boardPost.getUploadfile());

        if(fileName != null) {
            ImageFile imageFile = new ImageFile();

            imageFile.setSrc(fileName);
            imageFile.setBoard(board);
            imageFileRepository.save(imageFile);
        }

        BoardView boardView = new BoardView(board);

        return boardView;
    }

    public String uploadFile(MultipartFile uploadfile) {
        String filename = null;
        try {
            String originFilename = uploadfile.getOriginalFilename();
            int pos = originFilename.lastIndexOf(".");
            String ext = originFilename.substring(pos);
            filename = UUID.randomUUID().toString() + ext;
            String directory = System.getProperty("user.dir") + "/src/main/resources/static/img";
            String filepath = Paths.get(directory, filename).toString();

            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(new File(filepath)));
            stream.write(uploadfile.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return "./img/" + filename;
    }
}
