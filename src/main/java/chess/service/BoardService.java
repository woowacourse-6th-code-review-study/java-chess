package chess.service;

import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.game.PositionsFilter;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BoardService {

    private final RoomRepository roomRepository;
    private final BoardRepository boardRepository;

    public BoardService(RoomRepository roomRepository, BoardRepository boardRepository) {
        this.roomRepository = roomRepository;
        this.boardRepository = boardRepository;
    }

    public void movePiece(Position from, Position to, Long roomId) {
        Piece piece = findFromPositionPiece(from, roomId);
        Color currentTurn = findCurrentTurn(roomId, piece);
        List<Position> movablePositions = generateMovablePositions(piece, from, roomId);
        if (movablePositions.contains(to)) {
            movePiece(from, to, piece, roomId);
            roomRepository.updateRoomTurn(currentTurn.change(), roomId);
            return;
        }
        throw new IllegalArgumentException("기물을 해당 위치로 이동시킬 수 없습니다.");
    }

    private Piece findFromPositionPiece(Position from, Long roomId) {
        if (!boardRepository.existsPieceByPosition(from, roomId)) {
            throw new IllegalArgumentException("선택한 기물이 존재하지 않습니다.");
        }
        return boardRepository.findPieceByPosition(from, roomId);
    }

    private Color findCurrentTurn(Long roomId, Piece piece) {
        Color currentTurn = roomRepository.findTurnById(roomId);
        if (piece.isNotSameColor(currentTurn)) {
            throw new IllegalArgumentException("상대방의 기물을 움직일 수 없습니다. 현재 턴 : " + currentTurn);
        }
        return currentTurn;
    }

    private List<Position> generateMovablePositions(Piece piece, Position position, Long roomId) {
        Map<Direction, Queue<Position>> candidateAllPositions = piece.generateAllDirectionPositions(position);
        return new PositionsFilter(boardRepository, candidateAllPositions).generateValidPositions(piece, roomId);
    }

    private void movePiece(Position from, Position to, Piece piece, Long roomId) {
        boardRepository.deletePieceByPosition(from, roomId);
        boardRepository.deletePieceByPosition(to, roomId);
        boardRepository.savePiece(piece, to, roomId);
    }

    public boolean isCheckmate(Position position, Long roomId) {
        return boardRepository.existsPieceByPosition(position, roomId) && boardRepository.findPieceByPosition(position, roomId)
                .isKing();
    }

    public Map<Position, Piece> getAllPieces(Long roomId) {
        return boardRepository.findAllPieceByRoomId(roomId);
    }
}
