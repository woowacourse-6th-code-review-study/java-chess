package chess.dto;

import java.util.List;

public class RankDto {
    private final List<String> rank;

    public RankDto(List<String> rank) {
        this.rank = rank;
    }

    public List<String> getRank() {
        return rank;
    }
}
