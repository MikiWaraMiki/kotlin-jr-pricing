package jrpricing.discount.domain.discountrate

import jrpricing.discount.domain.exception.DomainException
import jrpricing.discount.domain.exception.ErrorCode

/**
 * 割引率
 */
class Percent(
    val value: Int
) {
    init {
        if (value < 0) throw DomainException("割引率を0未満にすることはできません", ErrorCode.INVALID_INPUT)

        if (value > 101) throw DomainException("割引率を101以上の数値にすることはできません", ErrorCode.INVALID_INPUT)
    }

    fun restOfHundred(): Percent {
        return Percent(100 - value)
    }
}