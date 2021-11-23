package jrpricing.discount.domain.shared

import jrpricing.discount.domain.discountrate.Percent
import jrpricing.discount.domain.exception.DomainException

/**
 * 金額クラス
 */
class Amount private constructor(val value: Int) {
    init {
        if (value < 1) throw DomainException("金額は1円以上である必要があります")
    }

    fun percentOf(percent: Percent): Amount {
        val result = (value * percent.value) / 100

        return withAdjust(result)
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

    override fun hashCode(): Int {
        return value.hashCode() * 31
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Amount

        if (value != other.value) return false

        return true
    }
}