package chess.service;

import chess.domain.ChessGame;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Score;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import chess.dto.BoardDto;
import chess.dto.PiecesDto;
import chess.dto.ScoreStatusDto;
import chess.repository.PieceRepository;
import chess.repository.TurnRepository;
import chess.repository.mapper.DomainMapper;

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
        return createNewChessGame();
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
        return ScoreStatusDto.of(whiteScore, blackScore, chessGame.selectHigherScoreTeam());
    }

    public ChessGame loadChessGame() {
        PiecesDto pieces = pieceRepository.findPieces().get();
        Team currentTurn = turnRepository.findCurrentTurn().get();
        ChessBoard chessBoard = DomainMapper.mapToBoard(pieces);
        return new ChessGame(chessBoard, currentTurn);
    }

    public boolean isChessGameNotEnd() {
        return loadChessGame().isNotEnd();
    }

    private BoardDto createNewChessGame() {
        ChessGame newChessGame = ChessGame.createNewChessGame();
        saveChessGame(newChessGame);
        return BoardDto.from(newChessGame.getChessBoard());
    }

    private void saveChessGame(ChessGame chessGame) {
        deleteSavedChessGame();
        ChessBoard chessBoard = chessGame.getChessBoard();
        PiecesDto piecesDto = PiecesDto.from(chessBoard);
        pieceRepository.savePieces(piecesDto);
        turnRepository.saveTurn(chessGame.getTurn());
    }

    private void deleteSavedChessGame() {
        pieceRepository.deleteAll();
        turnRepository.deleteAll();
    }

    private boolean isChessGameInProgress() {
        return pieceRepository.findPieces().isPresent();
    }
}
