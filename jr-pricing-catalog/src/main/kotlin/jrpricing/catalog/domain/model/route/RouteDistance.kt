package jrpricing.catalog.domain.model.route

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.exception.ErrorCode

/**
 * 経路の移動距離クラス
 */
class RouteDistance (
    private val value: Int
) {
    init {
        if (value < 1) throw DomainException("移動距離は1キロ以上である必要があります", ErrorCode.INVALID_INPUT)
    }

    fun roundTripDistance(): Int {
        return value * 2
    }

    fun halfTripDistance(): Int {
        return value
    }
}