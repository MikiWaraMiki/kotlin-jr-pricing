package jrpricing.fare.domain.passenger

import jrpricing.fare.domain.exception.DomainException
import jrpricing.fare.domain.exception.ErrorCode

/**
 * 乗車人数クラス
 */
class Passenger(
    val adultCount: Int,
    val childCount: Int
) {
    val total = adultCount.plus(childCount)

    init {
        if (total < 1)
            throw DomainException("乗車人数は1人以上である必要があります", ErrorCode.INVALID_INPUT)
    }

}