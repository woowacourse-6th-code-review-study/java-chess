package chess.domain.position;


import static chess.fixture.PositionFixtures.A1;
import static chess.fixture.PositionFixtures.A2;
import static chess.fixture.PositionFixtures.B1;
import static chess.fixture.PositionFixtures.B2;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DirectionTest {

    @DisplayName("방향을 계산할 수 있다 : a1 -> a2 = N")
    @Test
    void should_ReturnDirectionNorth() {
        assertThat(Direction.of(A1, A2)).isEqualTo(Direction.N);
    }

    @DisplayName("방향을 계산할 수 있다 : a1 -> b1 = E")
    @Test
    void should_ReturnDirectionEast() {
        assertThat(Direction.of(A1, B1)).isEqualTo(Direction.E);
    }

    @DisplayName("방향을 계산할 수 있다 : b1 -> a1 = W")
    @Test
    void should_ReturnDirectionWest() {
        assertThat(Direction.of(B1, A1)).isEqualTo(Direction.W);
    }

    @DisplayName("방향을 계산할 수 있다 : a2 -> a1 = S")
    @Test
    void should_ReturnDirectionSouth() {
        assertThat(Direction.of(A2, A1)).isEqualTo(Direction.S);
    }

    @DisplayName("방향을 계산할 수 있다 : a1 -> b2 = NE")
    @Test
    void should_ReturnDirectionNorthEast() {
        assertThat(Direction.of(A1, B2)).isEqualTo(Direction.NE);
    }

    @DisplayName("방향을 계산할 수 있다 : b2 -> a1 = SW")
    @Test
    void should_ReturnDirectionSouthWest() {
        assertThat(Direction.of(B2, A1)).isEqualTo(Direction.SW);
    }

    @DisplayName("방향을 계산할 수 있다 : b1 -> a2 = NW")
    @Test
    void should_ReturnDirectionNorthWest() {
        assertThat(Direction.of(B1, A2)).isEqualTo(Direction.NW);
    }

    @DisplayName("방향을 계산할 수 있다 : a2 -> b1 = SE")
    @Test
    void should_ReturnDirectionSoutWest() {
        assertThat(Direction.of(A2, B1)).isEqualTo(Direction.SE);
    }
}
