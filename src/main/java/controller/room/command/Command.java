package controller.room.command;

import dto.RoomDto;
import service.GameRoomService;

public interface Command {
    RoomDto execute(GameRoomService gameRoomService);
}
