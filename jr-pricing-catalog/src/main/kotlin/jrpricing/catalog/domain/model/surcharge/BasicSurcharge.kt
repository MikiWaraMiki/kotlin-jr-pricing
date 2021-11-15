package jrpricing.catalog.domain.model.surcharge

import jrpricing.catalog.domain.exception.DomainException
import jrpricing.catalog.domain.model.fare.BasicFare
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount

/**
 * 特急料金基本料金
 */
class BasicSurcharge private constructor(
    val routeId: RouteId,
    val amount: Amount
) {
    init {
        // TODO: 運賃と重複がある
        if (amount.value % 10 > 0) throw DomainException("特急料金は10円単位である必要があります")
    }

    companion object {
        fun reConstructor(routeId: RouteId, amount: Amount): BasicSurcharge {
            return BasicSurcharge(routeId, amount)
        }
    }
}