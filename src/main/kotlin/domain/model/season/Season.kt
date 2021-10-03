package domain.model.season

import lib.domainsupport.valueobject.MonthDayRange
import java.time.MonthDay

/**
 * 季節クラス
 */
class Season(
    startMonth: MonthDay,
    endMonth: MonthDay
) {
    private val seasonRange = MonthDayRange(startMonth, endMonth)

    fun contains(monthDay: MonthDay): Boolean {
        return seasonRange.contains(monthDay)
    }

    companion object {
        fun of(startMonthDayString: String, endMonthDayString: String): Season {
            return Season(
                MonthDay.parse("--${startMonthDayString}"),
                MonthDay.parse("--${endMonthDayString}")
            )
        }
    }
}