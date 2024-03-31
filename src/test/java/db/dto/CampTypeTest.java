package db.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import model.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CampTypeTest {

    @DisplayName("문자로 Camp를 찾는다.")
    @Test
    void findByColor() {
        assertAll(
                () -> assertThat(CampType.findByColorName("WHITE")).isEqualTo(Camp.WHITE),
                () -> assertThat(CampType.findByColorName("BLACK")).isEqualTo(Camp.BLACK)
        );
    }
}
