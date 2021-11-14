package jrpricing.demo.first.lib.domainsupport.valueobject

import java.time.LocalDate
import java.time.Month
import java.time.MonthDay

class MonthDayRange(
    val aStart: MonthDay,
    val aEnd: MonthDay
) {
    private val start: MonthDay
    private val end: MonthDay

    init {
        if (aEnd.isBefore(aStart)) throw IllegalArgumentException("終了月日を、開始月日より前にすることはできません")

        start = aStart
        end = aEnd
    }

    fun contains(monthDay: MonthDay): Boolean {
        if (monthDay.isBefore(start)) return false

        if (monthDay.isAfter(end)) return false

        return true
    }
}