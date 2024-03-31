package chess.domain.game;

public class Room {

    private final Long id;
    private final RoomName roomName;

    public Room(Long id, String name) {
        this.id = id;
        this.roomName = new RoomName(name);
    }

    public String getName() {
        return roomName.getValue();
    }

    public Long getId() {
        return id;
    }
}
