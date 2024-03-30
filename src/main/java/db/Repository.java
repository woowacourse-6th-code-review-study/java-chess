package db;

import db.dto.BoardDto;
import db.dto.MovingDto;
import db.dto.TurnDto;
import model.Camp;

public class Repository {

    // TODO 인터페이스로 변경하기
    private final MovingDao movingDao;
    private final TurnDao turnDao;
    private final BoardDao boardDao;

    public Repository(String database) {
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
        return turnDao.findTurn() != null;
    }

    public void save(final BoardDto board, final Camp camp) {
        boardDao.saveBoard(board);
        turnDao.saveTurn(camp);
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
}
