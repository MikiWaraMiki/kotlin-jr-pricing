package jrpricing.catalog.testdata.fare

import jrpricing.catalog.domain.model.fare.BasicFare
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount

object TestBasicFareRepository {
    fun create(
        routeId: RouteId = RouteId.create(),
        amount: Amount = Amount(1)
    ): BasicFare {
        return BasicFare.reConstructor(routeId, amount)
    }
}