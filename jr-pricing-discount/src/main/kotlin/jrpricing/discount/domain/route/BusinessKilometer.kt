package jrpricing.discount.domain.route

import jrpricing.discount.domain.exception.DomainException
import jrpricing.discount.domain.exception.ErrorCode

class BusinessKilometer(
    val value: Int
) {
    init {
        if (value < 1) throw DomainException("移動距離は１キロ以上です", ErrorCode.INVALID_INPUT)
    }
}