package domain.model.discount.rate

import lib.domainsupport.valueobject.MonthDayRange
import java.time.LocalDate
import java.time.MonthDay

/**
 * 乗車日に応じた割引率定義クラス
 */
enum class GroupDiscountRateCategory(
    private val start: String,
    private val end: String,
    private val rate: DiscountRate
) {
    DECEMBER_TEN_RATE("12-21", "12-31", DiscountRate.RATE_10),
    JANUARY_TEN_RATE("01-01", "01-10", DiscountRate.RATE_10),
    DEFAULT_RATE("01-01", "12-31", DiscountRate.RATE_15);

    private fun season(): MonthDayRange {
        return MonthDayRange(
            MonthDay.parse("--${start}"),
            MonthDay.parse("--${end}")
        )
    }

    companion object {
        fun rateFromDate(aDate: LocalDate): DiscountRate {
            val monthDay = MonthDay.from(aDate)

            val category = values().firstOrNull {
                val season = it.season()
                season.contains(monthDay)
            }

            if (category == null) throw IllegalArgumentException("存在しない日付です")

            return category.rate
        }
    }
}