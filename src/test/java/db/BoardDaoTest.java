package db;

import static org.assertj.core.api.Assertions.assertThat;

import db.dto.BoardDto;
import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardDaoTest {

    private final BoardDao boardDao = new BoardDao("chess_test");

    @BeforeEach
    void beforeEach() {
        boardDao.remove();
    }

    @Test
    @DisplayName("보드 저장 확인")
    void addBoard() {
        //given
        final BoardDto board = BoardDto.from(Board.create());

        //when
        boardDao.saveBoard(board);
        final BoardDto findBoard = boardDao.find();

        //then
        assertThat(board).isEqualTo(findBoard);
    }

    @DisplayName("테이블이 비어있다면 새로운 보드를 만든다.")
    @Test
    void findBoard() {
        //given
        boardDao.remove();
        final BoardDto expected = BoardDto.from(Board.create());

        //when
        final BoardDto boardDto = boardDao.find();

        //then
        assertThat(boardDto).isEqualTo(expected);
    }
}
