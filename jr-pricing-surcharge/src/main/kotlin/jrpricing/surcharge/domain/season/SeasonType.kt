package jrpricing.surcharge.domain.season

import jrpricing.surcharge.domain.exception.DomainException
import jrpricing.surcharge.domain.exception.ErrorCode
import java.time.LocalDate
import java.time.MonthDay
import java.util.*

/**
 * 季節区分
 */
enum class SeasonType(
    val typeName: String,
    private val seasonTypeCategorySet: EnumSet<SeasonTypeCategory>
) {
    PEAK("繁忙期", EnumSet.of(SeasonTypeCategory.END_OF_YEAR, SeasonTypeCategory.BEGINNING_OF_YEAR)),
    OFF_PEAK("閑散期", EnumSet.of(SeasonTypeCategory.OFF_PEAK)),
    REGULAR("通常", EnumSet.of(SeasonTypeCategory.REGULAR));

    fun contains(seasonTypeCategory: SeasonTypeCategory): Boolean {
        return seasonTypeCategorySet.contains(seasonTypeCategory)
    }

    companion object {
        fun of(monthDay: MonthDay): SeasonType {
            val seasonTypeCategory = SeasonTypeCategory.of(monthDay)
            val seasonType = values().firstOrNull {
                it.contains(seasonTypeCategory)
            } ?: throw DomainException("季節区分に存在しない日付です", ErrorCode.INVALID_INPUT)

            return seasonType
        }

        fun isPeak(monthDay: MonthDay): Boolean {
            return of(monthDay) == PEAK
        }

        fun isOffPeak(monthDay: MonthDay): Boolean {
            return of(monthDay) == OFF_PEAK
        }
    }
}