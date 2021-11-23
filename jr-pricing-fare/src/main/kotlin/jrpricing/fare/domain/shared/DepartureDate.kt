package jrpricing.fare.domain.shared

import jrpricing.fare.domain.exception.DomainException
import jrpricing.fare.domain.exception.ErrorCode
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * 乗車日
 */
class DepartureDate(
    val value: LocalDate
) {
    init {
        val current = LocalDate.now()

        if (value.isBefore(current))
            throw DomainException("乗車日時が不正な値です", ErrorCode.INVALID_INPUT)
    }

    fun monthDay(): String {
        return "${value.monthValue}-${value.dayOfMonth}"
    }
}