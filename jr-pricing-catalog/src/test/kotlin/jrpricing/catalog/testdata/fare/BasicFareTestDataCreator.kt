package jrpricing.catalog.testdata.fare

import jrpricing.catalog.domain.model.fare.BasicFare
import jrpricing.catalog.domain.model.fare.BasicFareRepository
import jrpricing.catalog.domain.model.route.Route
import jrpricing.catalog.domain.model.route.RouteRepository
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.testdata.route.TestRouteFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BasicFareTestDataCreator(
    @Autowired private val basicFareRepository: BasicFareRepository
) {
    fun create(
        route: Route = TestRouteFactory.create(),
        amount: Int = 1000
    ): BasicFare {
        val basicFare = TestBasicFareFactory.create(routeId = route.routeId, Amount(amount))
        basicFareRepository.insert(basicFare)

        return basicFare
    }
}