package jrpricing.surcharge.domain.model.season

import jrpricing.surcharge.domain.season.SeasonType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.MonthDay

class SeasonTypeTest {
    @Nested
    @DisplayName("繁忙期判定テスト")
    inner class IsPeakTest() {
        @ParameterizedTest
        @CsvSource(
            "12-25",
            "12-26",
            "12-30",
            "12-31",
            "01-01",
            "01-02",
            "01-09",
            "01-10"
        )
        fun `年末年始はtrueを返すこと`(input: String) {
            val monthDay = MonthDay.parse("--${input}")

            Assertions.assertTrue(SeasonType.isPeak(monthDay))
        }

        @ParameterizedTest
        @CsvSource(
            "12-24",
            "01-11",
            "02-01"
        )
        fun `年末年始以外はfalseを返すこと`(input: String) {
            val monthDay = MonthDay.parse("--${input}")

            Assertions.assertFalse(SeasonType.isPeak(monthDay))
        }
    }

    @Nested
    @DisplayName("閑散期判定テスト")
    inner class IsOffPeakTest() {
        @ParameterizedTest
        @CsvSource(
            "01-16",
            "01-17",
            "01-30",
            "01-31"
        )
        fun `閑散期である場合はtrueを返すこと`(input: String) {
            val monthDay = MonthDay.parse("--${input}")

            Assertions.assertTrue(SeasonType.isOffPeak(monthDay))

        }

        @ParameterizedTest
        @CsvSource(
            "01-15",
            "02-01"
        )
        fun `閑散期でない場合はfalseを返すこと`(input: String) {
            val monthDay = MonthDay.parse("--${input}")

            Assertions.assertFalse(SeasonType.isOffPeak(monthDay))
        }
    }
}