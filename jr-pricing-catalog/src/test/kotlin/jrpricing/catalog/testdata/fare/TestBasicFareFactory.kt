package jrpricing.catalog.testdata.fare

import jrpricing.catalog.domain.model.fare.BasicFare
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount

object TestBasicFareFactory {
    fun create(
        routeId: RouteId = RouteId.create(),
        amount: Amount = Amount(1000)
    ): BasicFare {
        return BasicFare.reConstructor(routeId, amount)
    }
}