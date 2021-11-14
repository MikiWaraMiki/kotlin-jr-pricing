package jrpricing.demo.first.domain.model.season

import org.junit.jupiter.api.*
import java.time.LocalDate

class SeasonTypeTest {

    @Nested
    @DisplayName("繁忙期の判定テスト")
    inner class IsPeakTest() {
        @Test
        fun `12月25日から1月10日の間は繁忙期であること`() {
            assertAll(
                {
                    Assertions.assertTrue(SeasonType.isPeak(LocalDate.parse("2021-12-25")))
                },
                {
                    Assertions.assertTrue(SeasonType.isPeak(LocalDate.parse("2021-12-26")))
                },
                {
                    Assertions.assertTrue(SeasonType.isPeak(LocalDate.parse("2021-01-09")))
                },
                {
                    Assertions.assertTrue(SeasonType.isPeak(LocalDate.parse("2021-01-10")))
                }
            )
        }
    }

    @Nested
    @DisplayName("閑散期の判定テスト")
    inner class IsOffPeakTest() {
        @Test
        fun `1月16日から1月30日の間は閑散期であること`() {
            assertAll(
                {
                    Assertions.assertTrue(SeasonType.isOffPeak(LocalDate.parse("2021-01-16")))
                },
                {
                    Assertions.assertTrue(SeasonType.isOffPeak(LocalDate.parse("2021-01-17")))
                },
                {
                    Assertions.assertTrue(SeasonType.isOffPeak(LocalDate.parse("2021-01-29")))
                },
                {
                    Assertions.assertTrue(SeasonType.isOffPeak(LocalDate.parse("2021-01-30")))
                }
            )
        }
    }

    @Nested
    @DisplayName("通常期の判定テスト")
    inner class RegularTest() {
        @Test
        fun `繁忙期・閑散期の日付ではない場合は通常期であること`() {
            assertAll(
                {
                    Assertions.assertFalse(SeasonType.isPeak(LocalDate.parse("2021-12-24")))
                    Assertions.assertFalse(SeasonType.isOffPeak(LocalDate.parse("2021-12-24")))
                },
                {
                    Assertions.assertFalse(SeasonType.isPeak(LocalDate.parse("2021-01-11")))
                    Assertions.assertFalse(SeasonType.isOffPeak(LocalDate.parse("2021-01-11")))
                },
                {
                    Assertions.assertFalse(SeasonType.isPeak(LocalDate.parse("2021-01-15")))
                    Assertions.assertFalse(SeasonType.isOffPeak(LocalDate.parse("2021-01-15")))
                },
                {
                    Assertions.assertFalse(SeasonType.isPeak(LocalDate.parse("2021-02-01")))
                    Assertions.assertFalse(SeasonType.isOffPeak(LocalDate.parse("2021-02-01")))
                },
            )
        }

    }
}