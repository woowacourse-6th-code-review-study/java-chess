package domain.board;

import domain.piece.Color;
import domain.piece.Empty;
import domain.piece.Piece;
import domain.piece.Type;
import domain.position.Position;
import domain.position.Route;
import dto.PieceDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessBoard {
    private final Map<Position, Piece> board;
    private GameStatus status;

    public ChessBoard(Map<Position, Piece> board, GameStatus status) {
        this.board = board;
        this.status = status;
    }

    public ChessBoard(Map<Position, Piece> board) {
        this(new HashMap<>(board), GameStatus.NOT_STARTED);
    }

    public void move(final Position source, final Position target) {
        validateEmptyPiece(source);
        validateTurn(source);
        validateEmptyRoute(source, target);
        validateLegalMove(source, target);
        movePiece(source, target);
        changeTurn();
    }

    private void validateEmptyPiece(final Position source) {
        final Piece piece = findPieceByPosition(source);
        if (piece.color().isNeutrality()) {
            throw new IllegalArgumentException("피스가 없습니다.");
        }
    }

    private void validateTurn(final Position source) {
        final Piece piece = findPieceByPosition(source);
        if (!this.status.isTurnOf(piece.color())) {
            throw new IllegalArgumentException(piece.color() + "의 턴입니다.");
        }
    }

    private void validateEmptyRoute(final Position source, final Position target) {
        final Route route = Route.create(source, target);
        if (route.isBlocked(board)) {
            throw new IllegalArgumentException("중간에 말이 있어서 이동할 수 없습니다.");
        }
    }

    private void validateLegalMove(final Position source, final Position target) {
        final Piece resourcePiece = findPieceByPosition(source);
        resourcePiece.validateMovement(source, target, findPieceByPosition(target));
    }

    private void movePiece(final Position source, final Position target) {
        final Piece piece = findPieceByPosition(source);
        board.remove(source);
        board.put(target, piece);
    }

    private Piece findPieceByPosition(final Position position) {
        return board.getOrDefault(position, Empty.getInstance());
    }

    private void changeTurn() {
        if (this.status.isTurnOf(Color.BLACK)) {
            this.status = GameStatus.WHITE_TURN;
            return;
        }
        this.status = GameStatus.BLACK_TURN;
    }

    public boolean isKingNotExist() {
        return board.values().stream()
                .filter(piece -> piece.type() == Type.KING)
                .count() != 2;
    }

    public void start() {
        this.status = GameStatus.WHITE_TURN;
    }

    public void end() {
        this.status = GameStatus.ENDED;
    }

    public boolean isGameRunning() {
        return this.status == GameStatus.ENDED;
    }

    public Score calculateScore() {
        return Score.calculate(board);
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(board);
    }

    public List<PieceDto> getPieces() {
        return board.entrySet().stream()
                .map(entry -> PieceDto.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    // TODO: DTO 수정하기
    public GameStatus getStatus() {
        return this.status;
    }
}
