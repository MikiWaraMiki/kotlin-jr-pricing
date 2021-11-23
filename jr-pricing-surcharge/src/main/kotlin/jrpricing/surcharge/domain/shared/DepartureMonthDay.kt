package jrpricing.surcharge.domain.shared

import java.time.LocalDate
import java.time.MonthDay

/**
 * 搭乗月日
 */
class DepartureMonthDay private constructor(
    val value: MonthDay
){
    companion object {
        fun fromLocalDate(localDate: LocalDate): DepartureMonthDay {
            return DepartureMonthDay(
                MonthDay.of(localDate.month, localDate.dayOfMonth)
            )
        }
    }
}