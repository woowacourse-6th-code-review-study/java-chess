package chess.domain.game;

import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.piece.Piece;
import chess.repository.BoardRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class PositionsFilter {

    private final BoardRepository boardRepository;
    private final Map<Direction, Queue<Position>> candidateAllPositions;

    public PositionsFilter(BoardRepository boardRepository,
                           Map<Direction, Queue<Position>> candidateAllPositions) {
        this.boardRepository = boardRepository;
        this.candidateAllPositions = candidateAllPositions;
    }

    public List<Position> generateValidPositions(Piece piece, Long roomId) {
        return candidateAllPositions.keySet().stream()
                .map(direction -> filterInvalidPositions(candidateAllPositions.get(direction), direction, piece, roomId))
                .flatMap(List::stream)
                .toList();
    }

    private List<Position> filterInvalidPositions(Queue<Position> expectedPositions,
                                                  Direction direction,
                                                  Piece piece,
                                                  Long roomId) {
        List<Position> result = new ArrayList<>();
        Position currentPosition = expectedPositions.poll();
        while (isEmptySpace(direction, piece, currentPosition, roomId)) {
            result.add(currentPosition);
            currentPosition = expectedPositions.poll();
        }
        if (isEnemySpace(direction, piece, currentPosition, roomId)) {
            result.add(currentPosition);
        }
        return result;
    }

    private boolean isEmptySpace(Direction direction, Piece piece, Position currentPosition, Long roomId) {
        return currentPosition != null
                && piece.isPawnMovePossible(direction)
                && !boardRepository.existsPieceByPosition(currentPosition, roomId);
    }

    private boolean isEnemySpace(Direction direction, Piece piece, Position currentPosition, Long roomId) {
        return currentPosition != null
                && piece.isPawnAttackPossible(direction)
                && boardRepository.existsPieceByPosition(currentPosition, roomId)
                && piece.isEnemy(boardRepository.findPieceByPosition(currentPosition, roomId));
    }
}
