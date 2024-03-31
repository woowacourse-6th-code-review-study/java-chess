package controller;

import domain.ChessGame;
import domain.board.ChessBoard;
import domain.board.ChessBoardFactory;
import dto.RoomDto;
import dto.TurnDto;
import repository.ChessGameService;
import view.InputView;
import view.OutputView;

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
            TurnDto turnDto = chessGameService.loadPreviousTurn(roomDto);
            return ChessBoardFactory.loadPreviousChessBoard(chessGameService.loadPreviousData(roomDto), turnDto.getTurn());
        } catch (IllegalArgumentException e) {
            return ChessBoardFactory.createInitialChessBoard();
        }
    }

    private void readCommandUntilValid() {
        try {
            InputView.readCommand().execute(chessGame);
        } catch (Exception e) {
            OutputView.printErrorMessage(e);
            readCommandUntilValid();
        }
    }

    private void updateGameStatus(final RoomDto roomDto) {
        if (chessGame.isGameOver()) {
            chessGameService.updateTurn(roomDto, new TurnDto("GAMEOVER", roomDto.room_id()));
            return;
        }
        chessGameService.updatePiece(roomDto, chessGame.getBoard().getPieces());
        chessGameService.updateTurn(roomDto, new TurnDto(chessGame.getTurn().name(), roomDto.room_id()));
    }
}
