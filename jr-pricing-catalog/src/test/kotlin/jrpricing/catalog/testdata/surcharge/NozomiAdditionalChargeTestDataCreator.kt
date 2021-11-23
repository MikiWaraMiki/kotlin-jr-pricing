package jrpricing.catalog.testdata.surcharge

import jrpricing.catalog.domain.model.route.Route
import jrpricing.catalog.domain.model.shared.Amount
import jrpricing.catalog.domain.model.surcharge.NozomiAdditionalCharge
import jrpricing.catalog.domain.model.surcharge.NozomiAdditionalChargeRepository
import jrpricing.catalog.testdata.route.TestRouteFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NozomiAdditionalChargeTestDataCreator(
    @Autowired private val nozomiAdditionalChargeRepository: NozomiAdditionalChargeRepository
) {
    fun create(
        route: Route = TestRouteFactory.create(),
        amount: Int = 1000
    ): NozomiAdditionalCharge {
        val nozomiAdditionalCharge = TestNozomiAdditionalChargeFactory.create(route.routeId, Amount(amount))
        nozomiAdditionalChargeRepository.insert(nozomiAdditionalCharge)

        return nozomiAdditionalCharge
    }
}