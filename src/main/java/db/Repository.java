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

    // TODO 인터페이스로 변경하기
    private final MovingDao movingDao;
    private final TurnDao turnDao;
    private final BoardDao boardDao;

    public Repository(final String database) {
        this.movingDao = new MovingDao(database);
        this.turnDao = new TurnDao(database);
        this.boardDao = new BoardDao(database);
    }

    public void remove() {
        turnDao.remove();
        boardDao.remove();
    }

    public void removeMoving() {
        movingDao.remove();
    }

    public boolean hasGame() {
        return movingDao.findByMovementId(1) != null;
    }

    public void save(final BoardDto board, final Camp camp, final Turn turn) {
        remove();

        boardDao.saveBoard(board);
        turnDao.saveTurn(camp, turn);
    }

    public void saveMoving(final MovingDto moving) {
        movingDao.addMoving(moving);
    }

    public BoardDto findBoard() {
        return boardDao.find();
    }

    public TurnDto findTurn() {
        return turnDao.findTurn();
    }

    public ChessGame findGame() {
        System.out.println("찾자 ");
        final BoardDto findBoard = findBoard();
        final TurnDto findTurn = findTurn();
        final List<MovingDto> findMoving = movingDao.findAll();
        final Board board = findBoard.convert();

        if (findTurn.count() < findMoving.size()) {
            restore(findTurn, findMoving, board);
        }
        //TODO 리팩터링
        final Camp camp = findMoving.size() % 2 == 0 ? Camp.WHITE : Camp.BLACK;
        final GameTurn gameTurn = new GameTurn(camp, new Turn(findMoving.size()));
        return new ChessGame(board, gameTurn);
    }

    private void restore(final TurnDto findTurn, final List<MovingDto> findMoving, final Board board) {
        for (int i = findTurn.count(); i < findMoving.size(); i++) {
            final MovingDto movingDto = findMoving.get(i);
            final Moving moving = new Moving(Position.from(movingDto.current()), Position.from(movingDto.next()));
            board.move(moving);
        }
    }
}
