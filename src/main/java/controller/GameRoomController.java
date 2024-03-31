package controller;

import dto.RoomDto;
import service.GameRoomService;
import view.InputView;
import view.OutputView;
import view.command.CommandInput;

import java.util.List;

public class GameRoomController {
    private final GameRoomService roomService;

    public GameRoomController(final GameRoomService roomService) {
        this.roomService = roomService;
    }

    public RoomDto run() {
        List<RoomDto> rooms = roomService.loadActiveRoomAll();
        if (rooms.isEmpty()) {
            System.out.println("방이 존재하지 않습니다. 새로운 방을 생성해 주세요.");
        } else {
            System.out.println("입장할 방을 선택해 주세요.");
            OutputView.printActiveRoomAll(rooms);
        }
        RoomDto roomDto = readCommandUntilValid();
        System.out.println(roomDto.room_id() + "번 방에 입장합니다.");
        return roomDto;
    }

    private RoomDto readCommandUntilValid() {
        try {
            return findRoom();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return readCommandUntilValid();
        }
    }

    private RoomDto findRoom() {
        CommandInput commandInput = InputView.readCommandInput();

        if (commandInput.prefix().equals("new")) {
            return roomService.createNewRoom();
        }
        if (commandInput.prefix().equals("room")) {
            String roomId = commandInput.getArguments().get(0);
            return roomService.findRoomById(roomId);
        }

        throw new IllegalArgumentException("방을 찾을 수 없습니다.");
    }
}
