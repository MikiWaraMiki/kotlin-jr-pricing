package jrpricing.catalog.domain.model.shared

import jrpricing.catalog.domain.exception.DomainException

/**
 * 金額クラス
 */
class Amount(
    val value: Int
) {
    init {
        if (value < 1) throw DomainException("金額は1円以上である必要があります")
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