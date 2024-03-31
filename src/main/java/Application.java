import controller.GameController;
import dao.ProductionPieceColorDao;
import dao.MysqlPieceDao;
import database.JdbcConnectionPool;
import domain.board.Board;
import domain.board.BoardInitializer;
import domain.board.Position;
import domain.game.ChessGame;
import domain.piece.Piece;
import view.InputView;
import view.OutputView;

import java.util.Map;

public class Application {
    public static void main(String[] args) {
        JdbcConnectionPool connectionPool = JdbcConnectionPool.getInstance();
        Map<Position, Piece> initialPiecePositions = BoardInitializer.initBoard();
        MysqlPieceDao mysqlPieceDao = new MysqlPieceDao(connectionPool);
        Board board = new Board(mysqlPieceDao, initialPiecePositions);

        ProductionPieceColorDao productionPieceColorDao = new ProductionPieceColorDao(connectionPool);
        ChessGame chessGame = new ChessGame(productionPieceColorDao, board);

        GameController gameController = new GameController(new InputView(), new OutputView(), chessGame);
        gameController.run();
    }
}
