package db;

import db.dto.BoardDto;
import db.dto.MovingDto;
import db.dto.TurnDto;
import java.util.List;
import model.Board;
import model.Camp;
import model.ChessGame;
import model.GameTurn;
import model.Turn;
import model.position.Moving;
import model.position.Position;

public class Repository {

    private final MovingDao movingDao;
    private final TurnDao turnDao;
    private final BoardDao boardDao;

    public Repository(final String database) {
        this.movingDao = new MovingDao(database);
        this.turnDao = new TurnDao(database);
        this.boardDao = new BoardDao(database);
    }

    public void removeAll() {
        turnDao.remove();
        boardDao.remove();
        movingDao.remove();
    }

    public boolean hasGame() {
        return movingDao.countMoving() > 0;
    }

    public void save(final BoardDto board, final TurnDto turnDto) {
        boardDao.remove();
        boardDao.saveBoard(board);
        turnDao.remove();
        turnDao.saveTurn(turnDto);
    }

    public void saveMoving(final MovingDto moving) {
        movingDao.addMoving(moving);
    }

    public ChessGame findGame() {
        final BoardDto findBoard = findBoard();
        final TurnDto findTurn = findTurn();
        final List<MovingDto> findMoving = movingDao.findAll();
        final Board board = findBoard.toBoard();
        if (unsaved(findTurn, findMoving)) {
            restore(findTurn, findMoving, board);
        }
        final Camp camp = findLastTurnCamp(findMoving);
        final GameTurn gameTurn = new GameTurn(camp, new Turn(findMoving.size()));
        return new ChessGame(board, gameTurn);
    }

    private BoardDto findBoard() {
        return boardDao.find();
    }

    private TurnDto findTurn() {
        return turnDao.findTurn();
    }

    private boolean unsaved(final TurnDto findTurn, final List<MovingDto> findMoving) {
        return findTurn.count() < findMoving.size();
    }

    private Camp findLastTurnCamp(final List<MovingDto> findMoving) {
        if (findMoving.size() % 2 == 0) {
            return Camp.WHITE;
        }
        return Camp.BLACK;
    }

    private void restore(final TurnDto findTurn, final List<MovingDto> findMoving, final Board board) {
        for (int i = findTurn.count(); i < findMoving.size(); i++) {
            final MovingDto movingDto = findMoving.get(i);
            final Moving moving = new Moving(Position.from(movingDto.current()), Position.from(movingDto.next()));
            board.move(moving);
        }
    }
}
