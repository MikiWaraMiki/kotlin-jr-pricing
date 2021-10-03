package lib.domainsupport.valueobject

import java.time.LocalDate
import java.time.MonthDay

class MonthDayRange(
    private val start: MonthDay,
    private val end: MonthDay
) {

    fun contains(monthDay: MonthDay): Boolean {
        return if (isSameMonthStartAndEnd()) {
            caseSameMonthContains(monthDay)
        } else {
            caseDifferentMonthContains(monthDay)
        }
    }

    private fun isSameMonthStartAndEnd(): Boolean {
        return start.month == end.month
    }

    private fun caseSameMonthContains(monthDay: MonthDay): Boolean {
        if (monthDay == start) return true

        if (monthDay == end) return true

        return monthDay.isAfter(start) && monthDay.isBefore(end)
    }

    private fun caseDifferentMonthContains(monthDay: MonthDay): Boolean {
        if (start.month == monthDay.month && start.dayOfMonth <= monthDay.dayOfMonth)
            return true

        if (end.month == monthDay.month && end.dayOfMonth >= monthDay.dayOfMonth)
            return true

        return false
    }
}