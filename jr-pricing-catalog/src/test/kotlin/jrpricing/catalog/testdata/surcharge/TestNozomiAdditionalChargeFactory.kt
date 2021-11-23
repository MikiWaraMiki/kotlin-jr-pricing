package jrpricing.catalog.testdata.surcharge

import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.domain.model.surcharge.NozomiAdditionalCharge

object TestNozomiAdditionalChargeFactory {
    fun create(
        routeId: RouteId = RouteId.create(),
        amount: Amount = Amount(1000)
    ): NozomiAdditionalCharge {
        return NozomiAdditionalCharge.reConstructor(routeId, amount)
    }
}