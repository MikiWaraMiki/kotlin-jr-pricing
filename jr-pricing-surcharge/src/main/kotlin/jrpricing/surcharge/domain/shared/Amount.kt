package jrpricing.surcharge.domain.shared

import jrpricing.surcharge.domain.exception.DomainException
import jrpricing.surcharge.domain.exception.ErrorCode

/**
 * 金額クラス
 */
class Amount private constructor(
    val value: Int
){
    init {
        if (value < 1) throw DomainException("金額は1円以上である必要があります", ErrorCode.INVALID_INPUT)
    }

    override fun hashCode(): Int {
        return super.hashCode() * 31
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Amount

        if (value != other.value) return false

        return true
    }

    companion object {
        fun of(value: Int): Amount {
            return Amount(value)
        }

        fun withAdjust(value: Int): Amount {
            val surplus = value % 10

            if (surplus == 0) return of(value)

            return of(value - surplus)
        }
    }
}