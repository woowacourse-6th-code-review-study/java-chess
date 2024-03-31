package chess.service;

import chess.domain.ChessGame;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardCreator;
import chess.domain.piece.Piece;
import chess.domain.piece.Score;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import chess.dto.PiecePlacementDto;
import chess.dto.ScoreStatusDto;
import chess.repository.PieceRepository;
import chess.repository.TurnRepository;
import chess.repository.mapper.DomainMapper;
import java.util.List;
import java.util.Map;

public class ChessGameService {
    private final PieceRepository pieceRepository;
    private final TurnRepository turnRepository;

    public ChessGameService(PieceRepository pieceRepository, TurnRepository turnRepository) {
        this.pieceRepository = pieceRepository;
        this.turnRepository = turnRepository;
    }

    public void startChessGame() {
        if (isChessGameInProgress()) {
            return;
        }
        createNewChessGame();
    }

    public void saveChessGame(ChessGame chessGame) {
        deleteSavedChessGame();
        ChessBoard chessBoard = chessGame.getChessBoard();
        Map<Position, Piece> board = chessBoard.getBoard();

        for (Position position : board.keySet()) {
            Piece piece = board.get(position);
            pieceRepository.savePiece(PiecePlacementDto.of(piece, position));
        }
        turnRepository.saveTurn(chessGame.getTurn());
    }

    public void movePiece(Position start, Position destination) {
        ChessGame chessGame = loadChessGame();
        chessGame.move(start, destination);
        saveChessGame(chessGame);
    }

    public ScoreStatusDto calculateScoreStatus() {
        ChessGame chessGame = loadChessGame();
        Score blackScore = chessGame.calculateTeamScore(Team.BLACK);
        Score whiteScore = chessGame.calculateTeamScore(Team.WHITE);
        Team winnerTeam = judgeWinnerTeam(whiteScore, blackScore);
        return ScoreStatusDto.of(whiteScore, blackScore, winnerTeam);
    }

    private void deleteSavedChessGame() {
        pieceRepository.deleteAll();
        turnRepository.deleteAll();
    }

    public ChessGame loadChessGame() {
        List<PiecePlacementDto> pieces = pieceRepository.findPieces().get();
        Team currentTurn = turnRepository.findCurrentTurn().get();
        ChessBoard chessBoard = DomainMapper.mapToBoard(pieces);

        return new ChessGame(chessBoard, currentTurn);
    }

    private boolean isChessGameInProgress() {
        if (pieceRepository.findPieces().isEmpty()) {
            return false;
        }
        return true;
    }

    //체스 게임 도메인으로 응집
    private void createNewChessGame() {
        ChessBoardCreator chessBoardCreator = new ChessBoardCreator();
        ChessBoard chessBoard = chessBoardCreator.create();
        saveChessGame(new ChessGame(chessBoard));
    }

    private Team judgeWinnerTeam(Score whiteTeamScore, Score blackTeamScore) {
        if (whiteTeamScore.isAbove(blackTeamScore)) {
            return Team.WHITE;
        }
        return Team.BLACK;
    }
}
