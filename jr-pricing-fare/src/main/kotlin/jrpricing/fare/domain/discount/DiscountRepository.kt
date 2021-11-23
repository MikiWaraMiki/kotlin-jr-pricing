package jrpricing.fare.domain.discount

import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.TripRoute
import jrpricing.fare.domain.shared.TripType

interface DiscountRepository {
    fun calc(basicFare: BasicFare, route: TripRoute, tripType: TripType): Amount?
}