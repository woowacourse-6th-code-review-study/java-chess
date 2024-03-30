package repository;

import domain.piece.Color;
import domain.position.Position;
import dto.PieceDto;
import dto.TurnDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ChessGameServiceTest {
    private static final PieceDto A2WhitePawn = new PieceDto("A", "2", "WHITE", "PAWN");
    private static final PieceDto B2WhitePawn = new PieceDto("B", "2", "WHITE", "PAWN");
    private static final PieceDto C2WhitePawn = new PieceDto("C", "2", "WHITE", "PAWN");
    private static final TurnDto whiteTurn = TurnDto.of(Color.WHITE);
    private static final TurnDto blackTurn = TurnDto.of(Color.BLACK);

    private final PieceDao pieceDao = new PieceMockDao();
    private final TurnDao turnDao = new TurnMockDao();
    private final ChessGameService chessGameService = new ChessGameService(pieceDao, turnDao);

    @BeforeEach
    void setData() {
        pieceDao.add(A2WhitePawn);
        pieceDao.add(B2WhitePawn);
        pieceDao.add(C2WhitePawn);
        turnDao.update(whiteTurn);
    }

    @AfterEach
    void rollback() {
        pieceDao.deleteAll();
        turnDao.deleteAll();
    }

    @Test
    void 이전_게임_데이터가_존재한다() {
        assertThat(chessGameService.isPreviousDataExist()).isTrue();
    }

    @Test
    void 이전_게임_데이터를_불러온다() {
        assertThat(chessGameService.loadPreviousData())
                .containsExactlyInAnyOrder(A2WhitePawn, B2WhitePawn, C2WhitePawn);
    }

    @Test
    void 이전_게임의_턴_데이터를_불러온다() {
        assertThat(chessGameService.loadPreviousTurn().getTurn())
                .isEqualTo(Color.WHITE);
    }

    @Test
    void 피스_데이터를_갱신한다() {
        PieceDto A3WhitePawn = new PieceDto("A", "3", "WHITE", "PAWN");
        PieceDto B3WhitePawn = new PieceDto("B", "3", "WHITE", "PAWN");
        PieceDto C3WhitePawn = new PieceDto("C", "3", "WHITE", "PAWN");

        List<PieceDto> pieceDtos = List.of(A3WhitePawn, B3WhitePawn, C3WhitePawn);

        chessGameService.updatePiece(pieceDtos);

        assertThat(chessGameService.loadPreviousData())
                .containsExactlyInAnyOrder(A3WhitePawn, B3WhitePawn, C3WhitePawn);
    }

    @Test
    void 턴_데이터를_갱신한다() {
        chessGameService.updateTurn(blackTurn);

        assertThat(chessGameService.loadPreviousTurn().getTurn())
                .isEqualTo(Color.BLACK);
    }

    @Test
    void 모든_데이터를_삭제한다() {
        chessGameService.deletePreviousData();

        assertThat(chessGameService.isPreviousDataExist()).isFalse();
    }

    static class PieceMockDao extends PieceDao {
        private Map<Position, PieceDto> repository = new HashMap<>();

        void add(final PieceDto piece) {
            Position position = new Position(piece.boardFile() + piece.boardRank());
            repository.put(position, piece);
        }

        PieceDto findOne(final String file, final String rank) {
            Position position = new Position(file + rank);
            if (!repository.containsKey(position)) {
                throw new IllegalArgumentException("데이터가 없습니다.");
            }
            return repository.get(position);
        }

        List<PieceDto> findAll() {
            return repository.values().stream().toList();
        }

        void deleteAll() {
            repository = new HashMap<>();
        }

        boolean hasRecords() {
            return repository.keySet().size() != 0;
        }
    }

    static class TurnMockDao extends TurnDao {
        private Color repository;

        TurnDto findOne() {
            if (repository == null) {
                throw new IllegalArgumentException("데이터가 없습니다.");
            }
            return TurnDto.of(repository);
        }

        void update(final TurnDto turnDto) {
            repository = turnDto.getTurn();
        }

        void deleteAll() {
            repository = null;
        }
    }
}
