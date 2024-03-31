package chess.service;

import chess.domain.ChessGame;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.Score;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import chess.dto.BoardDto;
import chess.dto.PieceDto;
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

    public BoardDto startChessGame() {
        if (isChessGameInProgress()) {
            return BoardDto.from(loadChessGame().getChessBoard());
        }
        createNewChessGame();
        return BoardDto.from(loadChessGame().getChessBoard());
    }

    public BoardDto movePiece(Position start, Position destination) {
        ChessGame chessGame = loadChessGame();
        chessGame.move(start, destination);
        saveChessGame(chessGame);
        return BoardDto.from(chessGame.getChessBoard());
    }

    public ScoreStatusDto calculateScoreStatus() {
        ChessGame chessGame = loadChessGame();
        Score blackScore = chessGame.calculateTeamScore(Team.BLACK);
        Score whiteScore = chessGame.calculateTeamScore(Team.WHITE);
        Team winnerTeam = judgeWinnerTeam(whiteScore, blackScore);
        return ScoreStatusDto.of(whiteScore, blackScore, winnerTeam);
    }

    public ChessGame loadChessGame() {
        List<PieceDto> pieces = pieceRepository.findPieces().get();
        Team currentTurn = turnRepository.findCurrentTurn().get();
        ChessBoard chessBoard = DomainMapper.mapToBoard(pieces);

        return new ChessGame(chessBoard, currentTurn);
    }

    public boolean isChessGameNotEnd() {
        ChessGame chessGame = loadChessGame();
        return chessGame.isNotEnd();
    }

    private void createNewChessGame() {
        ChessGame newChessGame = ChessGame.createNewChessGame();
        saveChessGame(newChessGame);
    }

    private void saveChessGame(ChessGame chessGame) {
        deleteSavedChessGame();
        ChessBoard chessBoard = chessGame.getChessBoard();
        Map<Position, Piece> board = chessBoard.getBoard();
        List<PieceDto> pieces = board.keySet().stream()
                .map(position -> PieceDto.of(board.get(position), position))
                .toList();
        pieceRepository.savePieces(pieces);
        turnRepository.saveTurn(chessGame.getTurn());
    }

    private void deleteSavedChessGame() {
        pieceRepository.deleteAll();
        turnRepository.deleteAll();
    }

    private boolean isChessGameInProgress() {
        return pieceRepository.findPieces().isPresent();
    }

    private Team judgeWinnerTeam(Score whiteTeamScore, Score blackTeamScore) {
        if (whiteTeamScore.isAbove(blackTeamScore)) {
            return Team.WHITE;
        }
        return Team.BLACK;
    }
}
