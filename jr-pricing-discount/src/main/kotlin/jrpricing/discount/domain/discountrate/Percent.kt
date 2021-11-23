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
        if (value < 0) throw DomainException("割引率が0以上の数値です", ErrorCode.INVALID_INPUT)
    }
}