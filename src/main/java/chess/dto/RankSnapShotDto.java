package chess.dto;

import java.util.List;

public class RankSnapShotDto {
    private final List<String> rank;

    public RankSnapShotDto(List<String> rank) {
        this.rank = rank;
    }

    public List<String> getRank() {
        return rank;
    }
}
