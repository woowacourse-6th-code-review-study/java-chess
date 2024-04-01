package db;

import static model.Fixtures.A2;
import static model.Fixtures.A3;
import static model.Fixtures.G6;
import static model.Fixtures.G7;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import db.dto.BoardDto;
import db.dto.MovingDto;
import db.dto.TurnDto;
import model.ChessGame;
import model.position.Moving;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RepositoryTest {

    private final Repository repository = new Repository("chess_test");

    @BeforeEach
    void beforeEach() {
        repository.removeAll();
    }

    @DisplayName("기보를 저장한다.")
    @Test
    void saveMoving() {
        repository.saveMoving(new MovingDto("WHITE", "a2", "a3"));
        assertThat(repository.hasGame()).isTrue();
    }

    @DisplayName("진행된 게입이 없으면 false를 반환한다.")
    @Test
    void hasNoGame() {
        assertThat(repository.hasGame()).isFalse();
    }

    @DisplayName("저장된 게임이 없을 때 새로운 게임을 만든다.")
    @Test
    void createNewChessGame() {
        final ChessGame game = repository.findGame();
        final ChessGame expected = ChessGame.setupStartingPosition();
        assertAll(
                () -> assertThat(game.getPieces()).isEqualTo(expected.getPieces()),
                () -> assertThat(game.getCamp()).isEqualTo(expected.getCamp()),
                () -> assertThat(game.getTurn()).isEqualTo(expected.getTurn())
        );
    }

    @Test
    @DisplayName("기보만 저장됐을 때 기보를 바탕으로 복구한다.")
    void restore() {
        //given
        final ChessGame expected = ChessGame.setupStartingPosition();
        expected.move(new Moving(A2, A3));

        //when
        repository.saveMoving(new MovingDto("WHITE", "a2", "a3"));
        final ChessGame game = repository.findGame();

        //then
        assertAll(
                () -> assertThat(game.getPieces()).isEqualTo(expected.getPieces()),
                () -> assertThat(game.getCamp()).isEqualTo(expected.getCamp()),
                () -> assertThat(game.getTurn()).isEqualTo(expected.getTurn())
        );
    }

    @Test
    @DisplayName("보드와 턴을 저장한다.")
    void saveBoardAndTurn() {
        //given
        final ChessGame expected = ChessGame.setupStartingPosition();
        expected.move(new Moving(A2, A3));
        repository.saveMoving(new MovingDto("WHITE", "a2", "a3"));
        expected.move(new Moving(G7, G6));
        repository.saveMoving(new MovingDto("BLACK", "g7", "g6"));

        final BoardDto boardDto = BoardDto.from(expected.getBoard());
        final TurnDto turnDto = TurnDto.from(expected.getCamp(), expected.getTurn());

        //when
        repository.save(boardDto, turnDto);

        //then
        final ChessGame game = repository.findGame();
        assertAll(
                () -> assertThat(game.getPieces()).isEqualTo(expected.getPieces()),
                () -> assertThat(game.getCamp()).isEqualTo(expected.getCamp()),
                () -> assertThat(game.getTurn()).isEqualTo(expected.getTurn())
        );
    }
}
