package chess.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import chess.domain.ChessGame;
import chess.domain.board.ChessBoard;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import chess.dto.BoardDto;
import chess.dto.PiecesDto;
import chess.dto.RankDto;
import chess.dto.ScoreStatusDto;
import chess.fixture.PositionFixtures;
import chess.repository.PieceRepository;
import chess.repository.TurnRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ChessGameServiceTest {
    private ChessGameService chessGameService;
    @Mock
    private PieceRepository pieceRepository;
    @Mock
    private TurnRepository turnRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chessGameService = new ChessGameService(pieceRepository, turnRepository);
    }

    @DisplayName("게임 시작 시 이미 진행되고 있는 게임이 존재하는 경우 이어서 진행된다")
    @Test
    void should_StartExistingGame_When_GameIsInProgress() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new Pawn(Team.WHITE));
        ChessBoard chessBoard = new ChessBoard(board);

        when(pieceRepository.findPieces()).thenReturn(Optional.of(PiecesDto.from(chessBoard)));
        when(turnRepository.findCurrentTurn()).thenReturn(Optional.of(Team.WHITE));

        BoardDto boardDto = chessGameService.startChessGame();
        List<RankDto> boardSnapShot = boardDto.getBoardSnapShot();
        List<String> rank1 = boardSnapShot.get(7).getRank();
        assertThat(rank1.get(0)).isEqualTo("p");
    }

    @DisplayName("게임 시작 시 이미 진행되고 있는 게임이 없는 경우 새로운 게임이 진행된다")
    @Test
    void should_StartNewGame_When_ThereIsNoGameInProgress() {
        when(pieceRepository.findPieces()).thenReturn(Optional.empty());
        when(turnRepository.findCurrentTurn()).thenReturn(Optional.empty());

        BoardDto boardDto = chessGameService.startChessGame();
        List<RankDto> boardSnapShot = boardDto.getBoardSnapShot();
        List<String> rank2 = boardSnapShot.get(6).getRank();
        assertThat(rank2).containsExactly("p", "p", "p", "p", "p", "p", "p", "p");
    }

    @DisplayName("움직임을 요청 받으면 저장되어있는 배치에서 움직임을 검증, 움직임을 수행한다")
    @Test
    void should_MoveBySavedPlacement_When_MoveCallArrive() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new Pawn(Team.WHITE));
        ChessBoard chessBoard = new ChessBoard(board);

        when(pieceRepository.findPieces()).thenReturn(Optional.of(PiecesDto.from(chessBoard)));
        when(turnRepository.findCurrentTurn()).thenReturn(Optional.of(Team.WHITE));
        BoardDto boardDto = chessGameService.movePiece(PositionFixtures.A1, PositionFixtures.A2);
        String a2 = boardDto.getBoardSnapShot().get(6).getRank().get(0);
        assertThat(a2).isEqualTo("p");
    }

    @DisplayName("저장된 보드 상태를 기반으로 점수를 계산할 수 있다")
    @Test
    void should_CalculateScoreBySavedStatus() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new Pawn(Team.WHITE));
        ChessBoard chessBoard = new ChessBoard(board);

        when(pieceRepository.findPieces()).thenReturn(Optional.of(PiecesDto.from(chessBoard)));
        when(turnRepository.findCurrentTurn()).thenReturn(Optional.of(Team.WHITE));

        ScoreStatusDto scoreStatusDto = chessGameService.calculateScoreStatus();
        assertAll(
                () -> assertThat(scoreStatusDto.getBlackTeamScore()).isEqualTo(0),
                () -> assertThat(scoreStatusDto.getWhiteTeamScore()).isEqualTo(1),
                () -> assertThat(scoreStatusDto.getWinnerTeam()).isEqualTo("WHITE")
        );
    }

    @DisplayName("저장된 게임을 로드할 수 있다")
    @Test
    void should_LoadInProgressGame() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new King(Team.WHITE));
        board.put(PositionFixtures.A2, new King(Team.BLACK));
        ChessBoard chessBoard = new ChessBoard(board);

        when(pieceRepository.findPieces()).thenReturn(Optional.of(PiecesDto.from(chessBoard)));
        when(turnRepository.findCurrentTurn()).thenReturn(Optional.of(Team.WHITE));

        ChessGame chessGame = chessGameService.loadChessGame();
        assertAll(
                () -> assertThat(chessGame.getTurn()).isEqualTo(Team.WHITE),
                () -> assertThat(chessGame.getChessBoard().positionIsEmpty(PositionFixtures.A1)).isFalse(),
                () -> assertThat(chessGame.getChessBoard().positionIsEmpty(PositionFixtures.A2)).isFalse()
        );
    }

    @DisplayName("저장된 게임이 끝난 게임인지 확인할 수 있다")
    @Test
    void should_CheckSavedGameIsEnd() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(PositionFixtures.A1, new King(Team.BLACK));
        ChessBoard chessBoard = new ChessBoard(board);

        when(pieceRepository.findPieces()).thenReturn(Optional.of(PiecesDto.from(chessBoard)));
        when(turnRepository.findCurrentTurn()).thenReturn(Optional.of(Team.WHITE));

        boolean chessGameNotEnd = chessGameService.isChessGameNotEnd();
        assertThat(chessGameNotEnd).isFalse();
    }
}
