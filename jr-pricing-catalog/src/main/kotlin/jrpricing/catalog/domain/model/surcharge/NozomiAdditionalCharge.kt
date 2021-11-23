package jrpricing.catalog.domain.model.surcharge

import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount

/**
 * のぞみ利用時追加料金
 */
class NozomiAdditionalCharge private constructor(
    val routeId: RouteId,
    val amount: Amount
){
    companion object {
        fun reConstructor(routeId: RouteId, amount: Amount): NozomiAdditionalCharge {
            return NozomiAdditionalCharge(routeId, amount)
        }
    }
}