package controller.game;

import domain.ChessGame;
import domain.board.ChessBoard;
import domain.board.ChessBoardFactory;
import dto.RoomDto;
import dto.StateDto;
import service.ChessGameService;
import view.InputView;
import view.OutputView;

import java.util.NoSuchElementException;

public class ChessGameController {
    private final ChessGameService chessGameService;
    private ChessGame chessGame;

    public ChessGameController(final ChessGameService chessGameService) {
        this.chessGameService = chessGameService;
    }

    public void start(final RoomDto roomDto) {
        chessGame = new ChessGame(initializeChessGame(roomDto));
        OutputView.printGameGuideMessage();
        while (chessGame.isPlaying()) {
            readCommandUntilValid();
        }
        updateGameStatus(roomDto);
    }

    private ChessBoard initializeChessGame(RoomDto roomDto) {
        try {
            StateDto stateDto = chessGameService.loadPreviousState(roomDto);
            return ChessBoardFactory.loadPreviousChessBoard(chessGameService.loadPreviousPieces(roomDto), stateDto.getState());
        } catch (NoSuchElementException e) {
            return ChessBoardFactory.createInitialChessBoard();
        }
    }

    private void readCommandUntilValid() {
        try {
            InputView.readGameCommand().execute(chessGame);
        } catch (Exception e) {
            OutputView.printErrorMessage(e);
            readCommandUntilValid();
        }
    }

    private void updateGameStatus(final RoomDto roomDto) {
        if (chessGame.isGameOver()) {
            chessGameService.updateState(new StateDto("GAMEOVER", roomDto.room_id()));
            return;
        }
        chessGameService.updatePieces(roomDto, chessGame.getBoard().getPieces());
        chessGameService.updateState(new StateDto(chessGame.getTurn().name(), roomDto.room_id()));
    }
}
