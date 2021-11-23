package jrpricing.surcharge.domain.model.season

import jrpricing.surcharge.domain.season.SeasonTypeCategory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.MonthDay

class SeasonTypeCategoryTest {
    @ParameterizedTest
    @CsvSource(
        "12-25",
        "12-26",
        "12-30",
        "12-31"
    )
    fun `日付から年末を取得できること`(input: String) {
        val monthDay = MonthDay.parse("--${input}")

        val seasonTypeCategory = SeasonTypeCategory.of(monthDay)
        Assertions.assertEquals(SeasonTypeCategory.END_OF_YEAR, seasonTypeCategory)
    }

    @ParameterizedTest
    @CsvSource(
        "01-01",
        "01-02",
        "01-09",
        "01-10"
    )
    fun `日付から年始を取得できること`(input: String) {
        val monthDay = MonthDay.parse("--${input}")

        val seasonTypeCategory = SeasonTypeCategory.of(monthDay)
        Assertions.assertEquals(SeasonTypeCategory.BEGINNING_OF_YEAR, seasonTypeCategory)
    }

    @ParameterizedTest
    @CsvSource(
        "01-16",
        "01-17",
        "01-30",
        "01-31"
    )
    fun `日付から閑散期を取得できること`(input: String) {
        val monthDay = MonthDay.parse("--${input}")

        val seasonTypeCategory = SeasonTypeCategory.of(monthDay)

        Assertions.assertEquals(SeasonTypeCategory.OFF_PEAK, seasonTypeCategory)
    }
}