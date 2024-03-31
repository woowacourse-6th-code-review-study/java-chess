package chess.dto;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.List;
import java.util.Map;

public class PiecesDto {
    private final List<PieceDto> pieces;

    public PiecesDto(List<PieceDto> pieces) {
        this.pieces = pieces;
    }

    public static PiecesDto from(ChessBoard chessBoard) {
        Map<Position, Piece> board = chessBoard.getBoard();
        List<PieceDto> pieces = board.keySet().stream()
                .map(position -> PieceDto.of(board.get(position), position))
                .toList();
        return new PiecesDto(pieces);
    }

    public List<PieceDto> getPieces() {
        return pieces;
    }
}
