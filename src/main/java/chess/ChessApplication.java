package chess;

import chess.dao.ChessDao;
import chess.dao.ChessGameRepository;
import chess.dao.ConnectionManager;
import chess.dao.MysqlChessGameRepository;
import chess.dao.MysqlPieceRepository;
import chess.dao.PieceRepository;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessApplication {

    private static final InputView INPUT_VIEW = new InputView();
    private static final OutputView OUTPUT_VIEW = new OutputView();

    public static void main(String[] args) {
        ConnectionManager connectionManager = new ConnectionManager();
        ChessGameRepository chessGameRepository = new MysqlChessGameRepository(connectionManager);
        PieceRepository pieceRepository = new MysqlPieceRepository(connectionManager);
        ChessDao chessDao = new ChessDao(chessGameRepository, pieceRepository);
        ChessService chessService = new ChessService(chessDao);

        ChessGame chessGame = new ChessGame(INPUT_VIEW, OUTPUT_VIEW, chessService);
        run(chessGame);
    }

    private static void run(ChessGame chessGame) {
        try {
            chessGame.run();
        } catch (IllegalArgumentException exception) {
            OUTPUT_VIEW.printExceptionMessage(exception);
        }
    }
}
