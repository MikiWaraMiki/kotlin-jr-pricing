package jrpricing.fare.domain.model.shared

import jrpricing.fare.domain.model.exception.DomainException
import jrpricing.fare.domain.model.exception.ErrorCode

/**
 * 金額クラス
 */
class Amount private constructor(
    val value: Int
){
    init {
        if (value < 1) throw DomainException("金額は1円以上である必要があります", ErrorCode.INVALID_INPUT)
    }

    companion object {
        fun of(value: Int): Amount {
            return Amount(value)
        }
    }
}