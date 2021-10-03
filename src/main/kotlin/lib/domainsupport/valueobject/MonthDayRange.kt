package lib.domainsupport.valueobject

import java.time.LocalDate
import java.time.MonthDay
import java.time.LocalDate.of as of1

class MonthDayRange(
    private val start: MonthDay,
    private val end: MonthDay
) {
    private val isEndNextYear: Boolean

    init {
        isEndNextYear = start.isAfter(end)
    }

    fun contains(monthDay: MonthDay): Boolean {

        if (start.month == monthDay.month && start.dayOfMonth <= monthDay.dayOfMonth)
            return true

        if (end.month == monthDay.month && end.dayOfMonth >= monthDay.dayOfMonth)
            return true

        return false
    }

    private fun startDateTime(): LocalDate {
        return start.atYear(LocalDate.now().year)
    }

    private fun endDateTime(): LocalDate {
        return if (isEndNextYear) {
            end.atYear(LocalDate.now().plusYears(1).year)
        } else {
            end.atYear(LocalDate.now().year)
        }
    }
}