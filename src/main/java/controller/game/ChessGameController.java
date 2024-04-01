package controller.game;

import domain.ChessGame;
import dto.RoomDto;
import service.ChessGameService;
import view.InputView;
import view.OutputView;

public class ChessGameController {
    private final ChessGameService chessGameService;

    public ChessGameController(ChessGameService chessGameService) {
        this.chessGameService = chessGameService;
    }

    public void run(RoomDto roomDto) {
        ChessGame chessGame = chessGameService.initializeChessGame(roomDto);
        OutputView.printGameGuideMessage();
        while (chessGame.isPlaying()) {
            readCommandUntilValid(chessGame);
        }
        chessGameService.saveChessGame(chessGame, roomDto);
    }

    private void readCommandUntilValid(ChessGame chessGame) {
        try {
            InputView.readGameCommand().execute(chessGame);
        } catch (Exception e) {
            OutputView.printErrorMessage(e);
            readCommandUntilValid(chessGame);
        }
    }
}
