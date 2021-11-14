package jrpricing.catalog.domain.model.fare

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount

/**
 * 運賃クラス
 */
class BasicFare private constructor(
    val routeId: RouteId,
    val amount: Amount
){
    init {
        if (amount.value % 10 > 0) throw DomainException("運賃は10円単位である必要があります")
    }

    companion object {
        fun reConstructor(routeId: RouteId, amount: Amount): BasicFare {
            return BasicFare(routeId, amount)
        }
    }
}