package domain.model.season

import java.time.LocalDate
import java.time.MonthDay
import java.util.*

/**
 * 季節区分
 */
enum class SeasonType(
    private val label: String,
    private val categoryList: EnumSet<SeasonTypeCategory>,
) {
    PEAK("繁忙期", EnumSet.of(SeasonTypeCategory.BEGINNING_OF_YEAR, SeasonTypeCategory.END_OF_YEAR)),
    OFF_PEAK("閑散期", EnumSet.of(SeasonTypeCategory.OFF_PEAK)),
    REGULAR("通常期", EnumSet.of(SeasonTypeCategory.REGULAR));

    fun contains(seasonTypeCategory: SeasonTypeCategory): Boolean {
        return categoryList.contains(seasonTypeCategory)
    }

    companion object {
        fun of(date: LocalDate): SeasonType {
            val seasonTypeCategory = SeasonTypeCategory.of(date)
            val seasonType = values().firstOrNull {
                it.contains(seasonTypeCategory)
            } ?: throw IllegalStateException("季節区分に存在しない日付です")

            return seasonType
        }

        fun isPeak(date: LocalDate): Boolean {
            return of(date) == PEAK
        }

        fun isOffPeak(date: LocalDate): Boolean {
            return of(date) == OFF_PEAK
        }
    }
}