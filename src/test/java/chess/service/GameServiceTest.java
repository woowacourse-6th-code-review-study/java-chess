package chess.service;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.game.Score;
import chess.domain.game.Winner;
import chess.domain.piece.Color;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import chess.repository.fake.FakeBoardRepository;
import chess.repository.fake.FakeRoomRepository;
import chess.repository.fake.WhitePieceRepository;
import chess.service.dto.ChessGameResult;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameServiceTest {

    private RoomRepository roomRepository;
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        roomRepository = new FakeRoomRepository();
        boardRepository = new FakeBoardRepository();
    }

    @DisplayName("점수를 계산하여 반환한다.")
    @Test
    void getScoreTest() {
        GameService gameService = new GameService(roomRepository, boardRepository);

        Map<Color, Score> score = gameService.getScore(0L);

        assertThat(score.get(Color.WHITE).score()).isEqualTo(7);
    }

    @DisplayName("살아있는 킹의 수가 2개일 때 점수를 이용하여 게임 결과를 생성한다.")
    @Test
    void generateGameResultTestWithScore() {
        GameService gameService = new GameService(roomRepository, boardRepository);

        ChessGameResult chessGameResult = gameService.generateGameResult(0L);

        assertThat(chessGameResult.getWinner()).isEqualTo(Winner.DRAW);
    }

    @DisplayName("살아있는 킹의 수가 1개일 때 킹의 색을 확인하고 게임 결과를 생성한다.")
    @Test
    void generateGameResultTestWithCheckmate() {
        GameService gameService = new GameService(roomRepository, new WhitePieceRepository());

        ChessGameResult chessGameResult = gameService.generateGameResult(0L);

        assertThat(chessGameResult.getWinner()).isEqualTo(Winner.WHITE_WIN);
    }
}
