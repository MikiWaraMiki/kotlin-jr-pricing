package jrpricing.surcharge.domain.season

import jrpricing.surcharge.domain.exception.DomainException
import jrpricing.surcharge.domain.exception.ErrorCode
import java.time.LocalDate
import java.time.MonthDay

enum class SeasonTypeCategory(val categoryName: String, val monthDayRange: MonthDayRange) {
    END_OF_YEAR("年末", MonthDayRange(MonthDay.of(12, 25), MonthDay.of(12,31))),
    BEGINNING_OF_YEAR("年始", MonthDayRange(MonthDay.of(1, 1), MonthDay.of(1,10))),
    OFF_PEAK("閑散期", MonthDayRange(MonthDay.of(1, 16), MonthDay.of(1, 31))),
    REGULAR("通年", MonthDayRange(MonthDay.of(1, 1), MonthDay.of(12, 31)));

    private fun contains(monthDay: MonthDay): Boolean {
        return monthDayRange.contains(monthDay)
    }

    companion object {
        fun of(monthDay: MonthDay): SeasonTypeCategory {
            return values().firstOrNull() {
                it.contains(monthDay)
            } ?: throw DomainException("季節区分カテゴリに存在しない日付です", ErrorCode.INVALID_INPUT)
        }
    }
}