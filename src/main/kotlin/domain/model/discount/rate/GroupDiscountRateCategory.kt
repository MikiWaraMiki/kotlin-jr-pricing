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
    TEN_RATE("12-21", "01-10", DiscountRate.RATE_10);

    private fun season(): MonthDayRange {
        return MonthDayRange(
            MonthDay.parse("--${start}"),
            MonthDay.parse("--${end}")
        )
    }

    companion object {
        private val DEFAULT_DISCOUNT_RATE = DiscountRate.RATE_15

        fun rateFromDate(aDate: LocalDate): DiscountRate {
            val monthDay = MonthDay.from(aDate)

            val category = values().firstOrNull {
                val season = it.season()
                season.contains(monthDay)
            }

            if (category == null) return DEFAULT_DISCOUNT_RATE

            return category.rate
        }
    }
}