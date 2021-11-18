package jrpricing.catalog.testdata.surcharge

import jrpricing.catalog.domain.model.route.Route
import jrpricing.catalog.domain.model.route.RouteId
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.domain.model.surcharge.BasicSurcharge
import jrpricing.catalog.domain.model.surcharge.BasicSurchargeRepository
import jrpricing.catalog.testdata.route.TestRouteFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BasicSurchargeTestDataCreator(
    @Autowired private val basicSurchargeRepository: BasicSurchargeRepository
) {
    fun create(
        route: Route = TestRouteFactory.create(),
        amount: Int = 1000
    ): BasicSurcharge {
        val basicSurcharge = TestBasicSurchargeFactory.create(routeId = route.routeId, Amount(amount))
        basicSurchargeRepository.insert(basicSurcharge)

        return basicSurcharge
    }
}