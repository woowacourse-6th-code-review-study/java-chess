package chess.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.board.position.Column;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import chess.repository.fake.FakeBoardRepository;
import chess.repository.fake.FakeRoomRepository;
import chess.repository.fake.NotExistsPieceRepository;
import chess.repository.fake.WhitePieceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardServiceTest {

    private RoomRepository roomRepository;
    private Position from;
    private Position to;

    @BeforeEach
    void setUp() {
        roomRepository = new FakeRoomRepository();
        from = new Position(Row.TWO, Column.C);
        to = new Position(Row.THREE, Column.C);
    }

    @DisplayName("선택한 위치에 기물이 존재하지 않으면 에러를 반환한다.")
    @Test
    void findFromPositionPieceTest() {
        BoardRepository boardRepository = new NotExistsPieceRepository();
        BoardService boardService = new BoardService(roomRepository, boardRepository);

        assertThatThrownBy(() -> boardService.movePiece(from, to, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택한 기물이 존재하지 않습니다.");
    }

    @DisplayName("이동시키려는 기물이 상대 기물이면 에러를 반환한다.")
    @Test
    void findCurrentTurnTest() {
        BoardRepository boardRepository = new WhitePieceRepository();
        BoardService boardService = new BoardService(roomRepository, boardRepository);

        assertThatThrownBy(() -> boardService.movePiece(from, to, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상대방의 기물을 움직일 수 없습니다. 현재 턴 : BLACK");
    }

    @DisplayName("이동 위치에 기물이 존재하고, 조회한 기물 타입이 킹이면 참을 반환한다.")
    @Test
    void isCheckmateTest() {
        BoardRepository boardRepository = new FakeBoardRepository();
        BoardService boardService = new BoardService(roomRepository, boardRepository);

        boolean isCheckmate = boardService.isCheckmate(from, 0L);

        Assertions.assertThat(isCheckmate).isTrue();
    }
}
