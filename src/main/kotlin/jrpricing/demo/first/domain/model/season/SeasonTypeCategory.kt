package jrpricing.demo.first.domain.model.season

import jrpricing.demo.first.lib.domainsupport.valueobject.MonthDayRange
import java.time.LocalDate
import java.time.MonthDay

/**
 * 季節区分カテゴリ
 */
enum class SeasonTypeCategory(
    private val label: String,
    private val start: String,
    private val end: String
) {
    END_OF_YEAR("年始", "12-25", "12-31"),
    BEGINNING_OF_YEAR("年始", "01-01", "01-10"),
    OFF_PEAK("閑散期", "01-16", "01-31"),
    REGULAR("平常", "01-01", "12-31");

    private fun contains(monthDay: MonthDay): Boolean {
        val range = MonthDayRange(
            MonthDay.parse("--${start}"),
            MonthDay.parse("--${end}")
        )

        return range.contains(monthDay)
    }

    companion object {
        fun of(date: LocalDate): SeasonTypeCategory {
            val monthDay = MonthDay.from(date)
            val category = values().firstOrNull {
                it.contains(monthDay)
            } ?: throw IllegalStateException("季節区分カテゴリに存在しない日付です")

            return category
        }
    }
}