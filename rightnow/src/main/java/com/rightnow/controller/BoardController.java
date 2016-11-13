package com.rightnow.controller;

import com.rightnow.details.LoginUserDetails;
import com.rightnow.domain.Board;
import com.rightnow.domain.BoardPost;
import com.rightnow.domain.BoardView;
import com.rightnow.repository.BoardRepository;
import com.rightnow.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeemangHan on 2016. 11. 7..
 */

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @RequestMapping(method = RequestMethod.GET)
    public List<BoardView> getBoard(@PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC, size=8) Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);

        List<BoardView> boardViewList = boardService.view(boardPage);

        return boardViewList;
    }

    @RequestMapping(value = "{boardId}", method = RequestMethod.GET)
    public BoardView getBoard(@PathVariable Integer boardId) {
        Board board = boardRepository.findOne(boardId);

        return boardService.view(board);
    }

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public BoardView writeBoard(BoardPost boardPost) {
        LoginUserDetails user = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int memberIdx = user.getIdx();

        BoardView boardView = boardService.post(boardPost, memberIdx);

        return boardView;
    }
}
