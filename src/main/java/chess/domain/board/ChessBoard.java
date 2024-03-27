package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.Score;
import chess.domain.piece.Team;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ChessBoard {
    private final Map<Position, Piece> board;

    public ChessBoard(Map<Position, Piece> board) {
        this.board = board;
    }

    public boolean positionIsEmpty(Position position) {
        return !board.containsKey(position);
    }

    public Piece findPieceByPosition(Position targetPosition) {
        if (positionIsEmpty(targetPosition)) {
            throw new NoSuchElementException("해당 위치에 기물이 존재하지 않습니다.");
        }
        return board.get(targetPosition);
    }

    public void move(Position start, Position destination) {
        if (canMove(start, destination)) {
            movePiece(start, destination);
            return;
        }
        throw new IllegalArgumentException("기물의 행마법에 어긋나는 이동입니다");
    }

    private void movePiece(Position start, Position destination) {
        Piece piece = findPieceByPosition(start);
        board.remove(start);
        board.put(destination, piece);
    }

    public boolean canMove(Position start, Position destination) {
        return findPieceByPosition(start).canMove(start, destination, this);
    }

    public boolean isPathHasObstacle(List<Position> path) {
        return !path.stream()
                .allMatch(this::positionIsEmpty);
    }

    public boolean isNoHostilePieceAt(Position position, Team team) {
        return positionIsEmpty(position) || !findPieceByPosition(position).isOtherTeam(team);
    }

    public Score calcualteDefaultScore(Team team) {
        return board.keySet().stream()
                .map(board::get)
                .filter(piece -> !piece.isOtherTeam(team))
                .map(Piece::score)
                .reduce(new Score(0), Score::add);
    }

    public int countSameFilePawn(Team team) {
        return Arrays.stream(File.values())
                .mapToInt(file -> countFriendlyPawnAtFile(team, file))
                .sum();
    }

    public boolean isBlackKingAlive() {
        return Arrays.stream(File.values())
                .flatMap(file -> Arrays.stream(Rank.values())
                        .map(rank -> new Position(file, rank)))
                .filter(position -> !this.positionIsEmpty(position))
                .map(this::findPieceByPosition)
                .filter(Piece::isKing)
                .anyMatch(Piece::isBlackTeam);
    }

    public boolean isWhiteKingAlive() {
        return Arrays.stream(File.values())
                .flatMap(file -> Arrays.stream(Rank.values())
                        .map(rank -> new Position(file, rank)))
                .filter(position -> !this.positionIsEmpty(position))
                .map(this::findPieceByPosition)
                .filter(Piece::isKing)
                .anyMatch(piece -> piece.isOtherTeam(Team.BLACK));
    }

    private int countFriendlyPawnAtFile(Team friendlyTeam, File file) {
        int count = (int) Arrays.stream(Rank.values())
                .map(rank -> new Position(file, rank))
                .filter(position -> !this.positionIsEmpty(position))
                .map(this::findPieceByPosition)
                .filter(Piece::isPawn)
                .filter(piece -> !piece.isOtherTeam(friendlyTeam))
                .count();
        if (count > 1) {
            return count;
        }
        return 0;
    }
}
