package jrpricing.surcharge.domain.season

import jrpricing.surcharge.domain.exception.DomainException
import jrpricing.surcharge.domain.exception.ErrorCode
import java.time.MonthDay

/**
 * 月日範囲
 */
class MonthDayRange(
    val start: MonthDay,
    val end: MonthDay
) {
    init {
        if (end.isBefore(start)) throw DomainException("終了月日を開始月日より後にはできません", ErrorCode.INVALID_INPUT)
    }

    fun contains(monthDay: MonthDay): Boolean {
        if (monthDay.isBefore(start)) return false

        if (monthDay.isAfter(end)) return false

        return true
    }
}