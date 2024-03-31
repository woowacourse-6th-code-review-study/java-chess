package chess.domain.game;

public class RoomName {

    private final String name;

    public RoomName(String name) {
        if (name.length() > 16) {
            throw new IllegalArgumentException("게임 방 이름 길이는 최대 16글자까지 가능합니다.");
        }
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
