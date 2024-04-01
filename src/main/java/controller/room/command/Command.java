package controller.room.command;

import dto.RoomDto;
import dto.UserDto;
import service.GameRoomService;

public interface Command {
    RoomDto execute(GameRoomService gameRoomService, UserDto user);
}
