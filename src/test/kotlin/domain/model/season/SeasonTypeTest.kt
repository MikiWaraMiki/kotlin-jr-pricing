package domain.model.season

import org.junit.jupiter.api.*
import java.time.LocalDate

class SeasonTypeTest {
    @Nested
    @DisplayName("ofメソッドのテスト")
    inner class OfTest() {
        @Test
        fun `12月25日から1月10日の間は繁忙期であること`() {
            assertAll(
                {
                    Assertions.assertEquals(SeasonType.PEAK, SeasonType.of(LocalDate.parse("2021-12-25")))
                },
                {
                    Assertions.assertEquals(SeasonType.PEAK, SeasonType.of(LocalDate.parse("2021-12-26")))
                },
                {
                    Assertions.assertEquals(SeasonType.PEAK, SeasonType.of(LocalDate.parse("2021-01-09")))
                },
                {
                    Assertions.assertEquals(SeasonType.PEAK, SeasonType.of(LocalDate.parse("2021-01-10")))
                }
            )
        }

        @Test
        fun `1月16日から1月30日の間は閑散期であること`() {
            assertAll(
                {
                    Assertions.assertEquals(SeasonType.OFF_PEAK, SeasonType.of(LocalDate.parse("2021-01-16")))
                },
                {
                    Assertions.assertEquals(SeasonType.OFF_PEAK, SeasonType.of(LocalDate.parse("2021-01-17")))
                },
                {
                    Assertions.assertEquals(SeasonType.OFF_PEAK, SeasonType.of(LocalDate.parse("2021-01-29")))
                },
                {
                    Assertions.assertEquals(SeasonType.OFF_PEAK, SeasonType.of(LocalDate.parse("2021-01-30")))
                }
            )
        }

        @Test
        fun `繁忙期・閑散期の日付ではない場合は通常期であること`() {
            assertAll(
                {
                    Assertions.assertEquals(SeasonType.REGULAR, SeasonType.of(LocalDate.parse("2021-12-24")))
                },
                {
                    Assertions.assertEquals(SeasonType.REGULAR, SeasonType.of(LocalDate.parse("2021-01-11")))
                },
            )
        }
    }
}