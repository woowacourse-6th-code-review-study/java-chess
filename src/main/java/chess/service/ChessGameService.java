package chess.service;

import chess.domain.ChessGame;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;
import chess.dto.PiecePlacementDto;
import chess.repository.PieceRepository;
import chess.repository.TurnRepository;
import chess.repository.mapper.DomainMapper;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ChessGameService {
    private final PieceRepository pieceRepository;
    private final TurnRepository turnRepository;

    public ChessGameService(PieceRepository pieceRepository, TurnRepository turnRepository) {
        this.pieceRepository = pieceRepository;
        this.turnRepository = turnRepository;
    }

    public void saveChessGame(ChessGame chessGame) throws SQLException {
        deleteSavedChessGame();
        ChessBoard chessBoard = chessGame.getChessBoard();
        Map<Position, Piece> board = chessBoard.getBoard();

        for (Position position : board.keySet()) {
            Piece piece = board.get(position);
            pieceRepository.savePiece(PiecePlacementDto.of(piece, position));
        }
        turnRepository.saveTurn(chessGame.getTurn());
    }

    private void deleteSavedChessGame() throws SQLException {
        pieceRepository.deleteAll();
    }

    public ChessGame loadChessGame() throws SQLException {
        List<PiecePlacementDto> pieces = pieceRepository.findPieces();
        ChessBoard chessBoard = DomainMapper.mapToBoard(pieces);
        Team currentTurn = turnRepository.findCurrentTurn();

        return new ChessGame(chessBoard, currentTurn);
    }
}
