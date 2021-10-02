package lib.domainsupport.valueobject

import java.time.LocalDate

/**
 * 日付範囲のサポートクラス
 */
class DateRange(val aStart: LocalDate, val aEnd: LocalDate) {
    private val start: LocalDate
    private val end: LocalDate

    init {
        if (aStart.isAfter(aEnd)) throw IllegalArgumentException("開始日を終了日より後の日付にすることはできません")

        start = aStart
        end = aEnd
    }

    fun contains(aDate: LocalDate): Boolean {
        if (aDate.isBefore(start)) return false

        if (aDate.isAfter(end)) return false

        return true
    }
}